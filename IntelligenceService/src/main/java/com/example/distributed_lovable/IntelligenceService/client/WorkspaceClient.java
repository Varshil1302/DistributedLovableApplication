package com.example.distributed_lovable.IntelligenceService.client;

import com.example.distributed_lovable.CommonLib.common_lib.dto.FileContentResponse;
import com.example.distributed_lovable.CommonLib.common_lib.dto.FileNodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "workspace-service",path = "/workspace")
public interface WorkspaceClient
{

    @GetMapping("/internal/v1/projects/{projectId}/files/content")
    public FileContentResponse getFileContent(@RequestHeader("Authorization") String authorization, @PathVariable Long projectId, @RequestParam String path);

    @GetMapping("/internal/v1/projects/{projectId}/files")
    public FileNodeResponse getFileTree(@RequestHeader("Authorization") String authorization,@PathVariable Long projectId);

}
