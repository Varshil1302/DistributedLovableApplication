package com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project;

public record FileNode(
        String path
) {
    @Override
    public String toString() {
        return path;
    }
}
