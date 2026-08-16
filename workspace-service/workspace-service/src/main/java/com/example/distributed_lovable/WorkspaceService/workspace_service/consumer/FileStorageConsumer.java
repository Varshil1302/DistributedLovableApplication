package com.example.distributed_lovable.WorkspaceService.workspace_service.consumer;

import com.example.distributed_lovable.CommonLib.common_lib.event.FileStoreResponseEvent;
import com.example.distributed_lovable.CommonLib.common_lib.event.FileStoredRequestEvent;
import com.example.distributed_lovable.WorkspaceService.workspace_service.entity.ProcessedEvent;
import com.example.distributed_lovable.WorkspaceService.workspace_service.repository.ProcessedEventRepository;
import com.example.distributed_lovable.WorkspaceService.workspace_service.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageConsumer
{

     private final ProjectFileService projectFileService;
     private final ProcessedEventRepository processedEventRepository;
     private final KafkaTemplate<String,Object> kafkaTemplate;

     @KafkaListener(topics = "file-storage-request-event",groupId = "workspaceGrp")
     public void fileStorageRequestConsume(FileStoredRequestEvent fileStoredRequestEvent)
     {
         if(processedEventRepository.existsById(fileStoredRequestEvent.sagaId())){
             log.info("Duplicate Saga detected: {}, Resending previous ACK.",fileStoredRequestEvent.sagaId());
             sendResponse(fileStoredRequestEvent,true,null);
             return;
         }

         try{
             log.info("Saving file: {}", fileStoredRequestEvent.filePath());
             projectFileService.saveFile(fileStoredRequestEvent.projectId(),fileStoredRequestEvent.filePath(),fileStoredRequestEvent.content());
             processedEventRepository.save(new ProcessedEvent(fileStoredRequestEvent.sagaId(), LocalDateTime.now()));
             sendResponse(fileStoredRequestEvent,true,null);
         } catch (RuntimeException e) {
             log.error("File Storage has been failed : {}",e.getMessage());
             sendResponse(fileStoredRequestEvent,false,e.getLocalizedMessage());
             throw new RuntimeException(e);
         }
     }

    private void sendResponse(FileStoredRequestEvent fileStoredRequestEvent, boolean isSuccess, String errorMessage)
    {
        FileStoreResponseEvent fileStoreResponseEvent = FileStoreResponseEvent.builder()
                                                                .sagaId(fileStoredRequestEvent.sagaId())
                                                                .errorMessage(errorMessage)
                                                                .success(isSuccess)
                                                                .projectId(fileStoredRequestEvent.projectId())
                                                                .build();

        kafkaTemplate.send("file-storage-response-event",fileStoreResponseEvent);
    }
}
