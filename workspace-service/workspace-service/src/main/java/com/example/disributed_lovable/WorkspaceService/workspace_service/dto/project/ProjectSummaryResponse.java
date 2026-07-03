package com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project;


import com.example.disributed_lovable.CommonLib.common_lib.enums.ProjectRole;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ProjectSummaryResponse(
        Long id,
        String name,
        ProjectRole role,
        Instant createdAt,
        Instant updatedAt
) {
}
