package com.example.disributed_lovable.WorkspaceService.workspace_service.service;


import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.FileContentResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.FileNodeResponse;

public interface ProjectFileService
{

     FileNodeResponse getFileTree(Long projectId, Long userId);

     FileContentResponse getFileContent(Long projectId, String path);

    void saveFile(Long projectId, String filePath, String fileContent);
}
