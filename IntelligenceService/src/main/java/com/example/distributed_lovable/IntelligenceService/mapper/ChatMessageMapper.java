package com.example.distributed_lovable.IntelligenceService.mapper;


import com.example.distributed_lovable.IntelligenceService.dto.chat.ChatResponse;
import com.example.distributed_lovable.IntelligenceService.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper
{
    List<ChatResponse> toChatMessage(List<ChatMessage> messages);
}
