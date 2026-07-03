package com.example.disributed_lovable.WorkspaceService.workspace_service.dto.member;



import com.example.disributed_lovable.CommonLib.common_lib.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull ProjectRole role) {
}
