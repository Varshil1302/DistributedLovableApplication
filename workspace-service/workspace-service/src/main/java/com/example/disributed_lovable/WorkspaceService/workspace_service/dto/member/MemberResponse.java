package com.example.disributed_lovable.WorkspaceService.workspace_service.dto.member;





import com.example.disributed_lovable.CommonLib.common_lib.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        String avatarUrl,
        ProjectRole role,
        Instant invitedAt
) {
}
