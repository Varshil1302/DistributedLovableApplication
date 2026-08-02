package com.example.distributed_lovable.WorkspaceService.workspace_service.controller;


import com.example.distributed_lovable.CommonLib.common_lib.security.JwtService;
import com.example.distributed_lovable.CommonLib.common_lib.dto.FileContentResponse;
import com.example.distributed_lovable.CommonLib.common_lib.dto.FileNodeResponse;
import com.example.distributed_lovable.WorkspaceService.workspace_service.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/files")
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
