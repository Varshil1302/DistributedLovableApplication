package com.example.distributed_lovable.IntelligenceService.controller;

import com.example.distributed_lovable.IntelligenceService.dto.chat.ChatRequest;
import com.example.distributed_lovable.IntelligenceService.dto.chat.ChatResponse;
import com.example.distributed_lovable.IntelligenceService.dto.chat.StreamResponse;
import com.example.distributed_lovable.IntelligenceService.service.AiCodeGenerationService;
import com.example.distributed_lovable.IntelligenceService.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController
{
    private final AiCodeGenerationService aiCodeGenerationService;
    private final ChatService chatService;

    @PostMapping(path = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<StreamResponse>> streamChat(@RequestBody ChatRequest chatRequest){
        return aiCodeGenerationService
                .streamResponse(chatRequest.message(),chatRequest.projectId())
                .map(data-> ServerSentEvent.<StreamResponse>builder()
                        .data(data)
                        .build());
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ChatResponse>> getChatHistory(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(chatService.getProjectChatHistory(projectId));
    }


}
