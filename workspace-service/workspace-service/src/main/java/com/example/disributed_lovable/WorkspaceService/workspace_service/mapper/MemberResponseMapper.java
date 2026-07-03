package com.example.disributed_lovable.WorkspaceService.workspace_service.mapper;


import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.member.MemberResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberResponseMapper
{
    @Mapping(source = "role",target = "role")
    @Mapping(source = "user.userId",target = "userId")
    @Mapping(source = "user.name",target = "name")
    @Mapping(source = "user.email",target = "email")
    MemberResponse toMemberResponse(ProjectMember projectMember);

    @Mapping(source = "role",target = "role")
    @Mapping(source = "user.userId",target = "userId")
    @Mapping(source = "user.name",target = "name")
    @Mapping(source = "user.email",target = "email")
    List<MemberResponse> toMemberResponse(List<ProjectMember> projectMember);


    @Mapping(target = "role", constant = "OWNER")
    //@JsonIgnore()
    MemberResponse fromUserOWNER(User user);
}
