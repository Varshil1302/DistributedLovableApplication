package com.example.distributed_lovable.IntelligenceService.dto.chat;


import com.example.disributed_lovable.CommonLib.common_lib.enums.ChatEventType;

public record ChatEventResponse(
        Long id,
        ChatEventType chatEventType,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metadata
) {
}
