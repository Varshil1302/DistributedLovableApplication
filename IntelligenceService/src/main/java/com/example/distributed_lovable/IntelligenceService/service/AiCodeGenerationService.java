package com.example.distributed_lovable.IntelligenceService.service;


import com.example.distributed_lovable.IntelligenceService.dto.chat.StreamResponse;
import reactor.core.publisher.Flux;

public interface AiCodeGenerationService
{
    Flux<StreamResponse> streamResponse(String message, Long projectId);
}
