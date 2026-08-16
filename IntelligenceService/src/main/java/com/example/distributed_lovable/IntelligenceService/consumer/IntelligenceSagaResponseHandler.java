package com.example.distributed_lovable.IntelligenceService.consumer;

import com.example.distributed_lovable.CommonLib.common_lib.enums.ChatEventStatus;
import com.example.distributed_lovable.CommonLib.common_lib.event.FileStoreResponseEvent;
import com.example.distributed_lovable.IntelligenceService.repository.ChatEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IntelligenceSagaResponseHandler
{

    private final ChatEventRepository chatEventRepository;

    @KafkaListener(topics = "file-storage-response-event",groupId = "intelligence-group")
    public void handleSagaResponse(FileStoreResponseEvent responseEvent)
    {
        chatEventRepository.findBySagaId(responseEvent.sagaId()).ifPresent(
                chatEvent -> {
                    if (!ChatEventStatus.PENDING.equals(chatEvent.getChatEventStatus())) {
                        log.info("Response for Saga {} is Already handled. ", chatEvent.getSagaId());
                        return;
                    }
                    if (responseEvent.success()) {
                        chatEvent.setChatEventStatus(ChatEventStatus.CONFIRMED);
                        log.info("Saga {} Condirmed", chatEvent.getSagaId());
                    } else {
                        log.warn("Saga {} failed, Remove Event", chatEvent.getSagaId());
                        chatEvent.setChatEventStatus(ChatEventStatus.FAILED);
                    }
                });
    }

}
