package com.example.distributed_lovable.IntelligenceService.service.impl;


import com.example.disributed_lovable.CommonLib.common_lib.enums.ChatEventType;
import com.example.disributed_lovable.CommonLib.common_lib.enums.MessageRole;
import com.example.disributed_lovable.CommonLib.common_lib.error.ResourceNotFoundException;
import com.example.disributed_lovable.CommonLib.common_lib.security.JwtService;
import com.example.distributed_lovable.IntelligenceService.client.WorkspaceClient;
import com.example.distributed_lovable.IntelligenceService.dto.chat.StreamResponse;
import com.example.distributed_lovable.IntelligenceService.entity.ChatEvent;
import com.example.distributed_lovable.IntelligenceService.entity.ChatMessage;
import com.example.distributed_lovable.IntelligenceService.entity.ChatSession;
import com.example.distributed_lovable.IntelligenceService.entity.ChatSessionId;
import com.example.distributed_lovable.IntelligenceService.llm.LLMResponseParser;
import com.example.distributed_lovable.IntelligenceService.llm.PromptUtils;
import com.example.distributed_lovable.IntelligenceService.llm.advisors.FileTreeContextAdvisor;
import com.example.distributed_lovable.IntelligenceService.llm.tools.CodeGenerationTools;
import com.example.distributed_lovable.IntelligenceService.repository.ChatEventRepository;
import com.example.distributed_lovable.IntelligenceService.repository.ChatMessageRepository;
import com.example.distributed_lovable.IntelligenceService.repository.ChatSessionRepository;
import com.example.distributed_lovable.IntelligenceService.service.AiCodeGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiCodeGenerationServiceImpl implements AiCodeGenerationService
{

    private final ChatClient chatClient;
    private final JwtService jwtService;
    private final WorkspaceClient workspaceClient;
    private final FileTreeContextAdvisor fileTreeContextAdvisor;
    private final LLMResponseParser llmResponseParser;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatEventRepository chatEventRepository;

    private static final Pattern FILE_TAG_PATTERN = Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>",Pattern.DOTALL);

    @Override
    public Flux<StreamResponse> streamResponse(String message, Long projectId) {
        Long userId = jwtService.getCurrentUser();
        log.info("User is ::: "+userId);
        ChatSession chatSession = createChatSessionIfNotExists(projectId,userId);
        AtomicReference<Long> startTime = new AtomicReference<>(System.currentTimeMillis());
        AtomicReference<Long> endTime = new AtomicReference<>(0L);
        AtomicReference<Usage> usages = new AtomicReference<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String jwt = (String) authentication.getCredentials();

        Map<String,Object> advisorParams = Map.of("userId",userId,"projectId",projectId,"jwt", jwt);

        CodeGenerationTools codeGenerationTools = new CodeGenerationTools(workspaceClient,jwt,projectId);

        StringBuilder fullResponse = new StringBuilder();

        return chatClient.prompt()
                .system(PromptUtils.SYSTEM_Prompt)
                .user(message)
                .tools(codeGenerationTools)
                .advisors(advisorSpec->{
                    advisorSpec.params(advisorParams);
                    advisorSpec.advisors(fileTreeContextAdvisor);
                })
                .stream()
                .chatResponse()
                .filter(chatResponse -> {
                            return chatResponse != null &&
                                    chatResponse.getResult() != null &&
                                    chatResponse.getResult().getOutput() != null &&
                                    chatResponse.getResult().getOutput().getText() != null;
//                            return chatResponse.getResult().getOutput().getText() != null
                        }
                )
                .doOnNext(response->{
                    String sb= response.getResult().getOutput().getText();
                    if(sb!=null && !sb.isEmpty() && endTime.equals(0L))
                    {
                        endTime.set(System.currentTimeMillis());
                    }
                    fullResponse.append(sb);
                })
                .doOnComplete(
                        ()->{
                            Schedulers.boundedElastic().schedule(()->{
                                long duration = (endTime.get()-startTime.get())/1000;
                                finalizeChats(message,chatSession,fullResponse.toString(),duration,usages.get());
                            });
                        })
                .doOnError(error->log.error("Error during streaming for projectId: {}",projectId))
                .handle((resp, sink) -> {
                    var result = resp != null ? resp.getResult() : null;
                    var output = result != null ? result.getOutput() : null;
                    var text   = output != null ? output.getText() : null;

                    if (text != null && !text.isEmpty()) {
                        sink.next(new StreamResponse(text));
                    }
                    // else: ignore non-text events
                });
    }

    private void finalizeChats(String userMessage , ChatSession chatSession, String fullText, Long duration, Usage usage)
    {
           //Save the User Message.
           chatMessageRepository.save(
                   ChatMessage.builder()
                           .role(MessageRole.USER)
                           .chatSession(chatSession)
                           .content(userMessage)
                           .build()
           );

          //Save the Assistant Message.
        ChatMessage assistantchatMessage = ChatMessage.builder()
                                       .role(MessageRole.ASSISTANT)
                                        .content("Assistant Messages here..")
                                       .chatSession(chatSession)
                                       .build();

        assistantchatMessage = chatMessageRepository.save(assistantchatMessage);

        List<ChatEvent> chatEventList = llmResponseParser.parseChatEvents(fullText,assistantchatMessage);

        chatEventList.addFirst(ChatEvent.builder()
                                    .chatEventType(ChatEventType.THOUGHT)
                                    .chatMessage(assistantchatMessage)
                                    .sequenceOrder(0)
                                    .content("Thought For "+duration+" sec.")
                                    .build());

        chatEventList.stream()
                .filter(event->event.getChatEventType()== ChatEventType.FILE_EDIT)
                .forEach(event->{
                   // projectFileService.saveFile(chatSession.getProject().getId(),event.getFilePath(),event.getContent())
                        });
        chatEventRepository.saveAll(chatEventList);
    }

    private void parseAndSaveFile(String fullResponse, Long projectId) {

        Matcher matcher = FILE_TAG_PATTERN.matcher(fullResponse);

        while (matcher.find())
        {
            String filePath = matcher.group(1);
            String fileContent = matcher.group(2).trim();
           // projectFileService.saveFile(projectId, filePath,fileContent);

        }
    }

    private ChatSession createChatSessionIfNotExists(Long projectId, Long userId)
    {
        ChatSessionId chatSessionId = ChatSessionId.builder()
                                       .userId(userId).projectId(projectId)
                                       .build();

        ChatSession chatSession = chatSessionRepository.findById(chatSessionId).orElse(null);


        if(chatSession==null){
            ChatSession newchatSession = ChatSession.builder()
                                                    .chatSessionId(chatSessionId)
                                                     .createdAt(Instant.now())
                                                     .build();

            chatSession = chatSessionRepository.save(newchatSession);
        }
        return chatSession;
    }
}
