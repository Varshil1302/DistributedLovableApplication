package com.example.disributed_lovable.WorkspaceService.workspace_service.controller;


import com.example.disributed_lovable.CommonLib.common_lib.security.JwtService;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.FileContentResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.FileNodeResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/files")
@RequiredArgsConstructor
public class FilesController
{
    private final ProjectFileService projectFileService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<FileNodeResponse> getFileTree(@PathVariable Long projectId) {
        Long userId = jwtService.getCurrentUser();
        return ResponseEntity.ok(projectFileService.getFileTree(projectId, userId));
    }

    @GetMapping("/content") // /src/hooks/get-user-hook.jsx
    public ResponseEntity<FileContentResponse> getFile(
            @PathVariable Long projectId,
            @RequestParam String path
    ) {
        Long userId = jwtService.getCurrentUser();
        return ResponseEntity.ok(projectFileService.getFileContent(projectId, path));
    }



}
