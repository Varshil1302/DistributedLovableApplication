package com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
