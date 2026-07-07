package com.example.distributed_lovable.IntelligenceService.service;

import com.example.distributed_lovable.IntelligenceService.dto.chat.ChatResponse;

import java.util.List;

public interface ChatService
{
    List<ChatResponse>  getProjectChatHistory(Long projectId);
}
