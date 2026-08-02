package com.example.distributed_lovable.IntelligenceService.dto.chat;



import com.example.distributed_lovable.CommonLib.common_lib.enums.MessageRole;
import com.example.distributed_lovable.IntelligenceService.entity.ChatSession;

import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,
        ChatSession chatSession,
        String content,
        MessageRole role,
        List<ChatEventResponse> chatEventList,
        Integer tokenUsed,
        Instant createdAt
) {
}
