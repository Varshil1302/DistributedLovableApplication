package com.example.distributed_lovable.WorkspaceService.workspace_service.consumer;

import com.example.distributed_lovable.CommonLib.common_lib.event.FileStoredRequestEvent;
import com.example.distributed_lovable.WorkspaceService.workspace_service.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageConsumer
{

     private final ProjectFileService projectFileService;

     @KafkaListener(topics = "file-storage-request-event",groupId = "workspaceGrp")
     public void fileStorageRequestConsume(FileStoredRequestEvent fileStoredRequestEvent)
     {
         log.info("Saving file: {}", fileStoredRequestEvent.filePath());
         projectFileService.saveFile(fileStoredRequestEvent.projectId(),fileStoredRequestEvent.filePath(),fileStoredRequestEvent.content());
     }
}
