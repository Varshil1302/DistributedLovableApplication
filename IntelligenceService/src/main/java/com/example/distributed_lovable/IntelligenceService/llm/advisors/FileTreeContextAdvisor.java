package com.example.distributed_lovable.IntelligenceService.llm.advisors;


import com.example.disributed_lovable.CommonLib.common_lib.dto.FileNode;
import com.example.distributed_lovable.IntelligenceService.client.WorkspaceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileTreeContextAdvisor implements StreamAdvisor
{

    private final WorkspaceClient workspaceClient;

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain)
    {
        Map<String,Object> context = chatClientRequest.context();
        Long projectId= Long.parseLong(context.getOrDefault("projectId",0).toString());
        Long userId = Long.parseLong(context.getOrDefault("userId",0).toString());
        String jwt = (String) context.get("jwt");
        ChatClientRequest augmentedChatClientRequest = augmentRequestWithFileTree(chatClientRequest,projectId,userId,jwt);
        return streamAdvisorChain.nextStream(augmentedChatClientRequest);
    }

    private ChatClientRequest augmentRequestWithFileTree(ChatClientRequest chatClientRequest, Long projectId, Long userId,String jwt)
    {
        List<Message> incomingMessage = chatClientRequest.prompt().getInstructions();
        Message systemPropmt = incomingMessage.stream()
                .filter(m->m.getMessageType()== MessageType.SYSTEM)
                .findFirst()
                .get();

        List<Message> userMessage = incomingMessage.stream().filter(m->m.getMessageType()!=MessageType.SYSTEM).toList();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("AUTH:::"+auth);

        log.info("Advisor thread = {}", Thread.currentThread().getName());
        log.info("Authentication = {}", auth);

        List<FileNode> fileTrees = workspaceClient.getFileTree("Bearer " + jwt,projectId).files();
        log.info("Files "+fileTrees);
        String fileTreeContext = "\n\n ---- FILE_TREE ----\n"+fileTrees.toString();
        log.info("Files Conexts:::"+fileTreeContext);


        List<Message> allMessages = new ArrayList<>();

        if(systemPropmt!=null)
        {
            allMessages.add(systemPropmt);
        }
        allMessages.add(new SystemMessage(fileTreeContext));
        allMessages.addAll(userMessage);
        return chatClientRequest.mutate().prompt(new Prompt(allMessages, chatClientRequest.prompt().getOptions())).build();
    }

    @Override
    public String getName() {
        return "FileTreeContextAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
