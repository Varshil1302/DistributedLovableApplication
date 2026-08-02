package com.example.distributed_lovable.IntelligenceService.service.impl;


import com.example.distributed_lovable.CommonLib.common_lib.security.JwtService;
import com.example.distributed_lovable.IntelligenceService.dto.chat.ChatResponse;
import com.example.distributed_lovable.IntelligenceService.entity.ChatMessage;
import com.example.distributed_lovable.IntelligenceService.entity.ChatSession;
import com.example.distributed_lovable.IntelligenceService.entity.ChatSessionId;
import com.example.distributed_lovable.IntelligenceService.mapper.ChatMessageMapper;
import com.example.distributed_lovable.IntelligenceService.repository.ChatMessageRepository;
import com.example.distributed_lovable.IntelligenceService.repository.ChatSessionRepository;
import com.example.distributed_lovable.IntelligenceService.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService
{

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final JwtService jwtService;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {
        Long userId = jwtService.getCurrentUser();
        ChatSession chatSession = chatSessionRepository.getReferenceById(ChatSessionId.builder().projectId(projectId).userId(userId).build());
        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatSession(chatSession);
        return chatMessageMapper.toChatMessage(chatMessageList);
    }
}
