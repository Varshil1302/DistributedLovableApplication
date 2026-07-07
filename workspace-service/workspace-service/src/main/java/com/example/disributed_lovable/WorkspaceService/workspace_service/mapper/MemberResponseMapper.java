package com.example.disributed_lovable.WorkspaceService.workspace_service.mapper;


import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.member.MemberResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberResponseMapper
{

    @Mapping(source = "id.userId",target = "userId")
    MemberResponse toMemberResponse(ProjectMember projectMember);

    @Mapping(source = "id.userId",target = "userId")
    List<MemberResponse> toMemberResponse(List<ProjectMember> projectMember);
}
