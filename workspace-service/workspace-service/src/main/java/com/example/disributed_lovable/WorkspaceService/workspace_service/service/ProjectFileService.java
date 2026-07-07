package com.example.disributed_lovable.WorkspaceService.workspace_service.service;


import com.example.disributed_lovable.CommonLib.common_lib.dto.FileContentResponse;
import com.example.disributed_lovable.CommonLib.common_lib.dto.FileNodeResponse;

public interface ProjectFileService
{

     FileNodeResponse getFileTree(Long projectId, Long userId);

     FileContentResponse getFileContent(Long projectId, String path);

    void saveFile(Long projectId, String filePath, String fileContent);
}
