package com.example.distributed_lovable.WorkspaceService.workspace_service.service;



import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.member.InviteMemberRequest;
import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.member.MemberResponse;
import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.member.UpdateMemberRoleRequest;

import java.util.List;

public interface ProjectMemberService
{

     List<MemberResponse> getProjectMembers(Long projectId);

     MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

     MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request);

     void deleteProjectMember(Long projectId, Long memberId);
}
