package com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project;

import jakarta.validation.constraints.NotNull;

public record ProjectRequest(
      @NotNull String name
) {
}
