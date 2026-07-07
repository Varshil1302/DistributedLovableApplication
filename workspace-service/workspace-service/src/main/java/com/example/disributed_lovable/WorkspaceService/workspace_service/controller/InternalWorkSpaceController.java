package com.example.disributed_lovable.WorkspaceService.workspace_service.controller;

import com.example.disributed_lovable.CommonLib.common_lib.dto.FileContentResponse;
import com.example.disributed_lovable.CommonLib.common_lib.dto.FileNode;
import com.example.disributed_lovable.CommonLib.common_lib.dto.FileNodeResponse;
import com.example.disributed_lovable.CommonLib.common_lib.security.JwtService;
import com.example.disributed_lovable.WorkspaceService.workspace_service.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1")
public class InternalWorkSpaceController
{
    private final ProjectFileService projectFileService;
    private final JwtService jwtService;

    @GetMapping("/projects/{projectId}/files/content")
    public FileContentResponse getFileContent(
            @PathVariable Long projectId,
            @RequestParam String path
    )
    {
       return projectFileService.getFileContent(projectId,path);
    }

    @GetMapping("/projects/{projectId}/files")
    public FileNodeResponse getFileTree(@PathVariable Long projectId)
    {
        Long userId = jwtService.getCurrentUser();
        log.info("USER IID IS:::"+userId);
        return projectFileService.getFileTree(projectId,userId);
    }
}
