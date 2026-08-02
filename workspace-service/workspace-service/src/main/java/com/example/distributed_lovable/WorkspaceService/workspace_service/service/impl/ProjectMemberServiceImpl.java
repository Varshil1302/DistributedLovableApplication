package com.example.distributed_lovable.WorkspaceService.workspace_service.service.impl;


import com.example.distributed_lovable.CommonLib.common_lib.dto.Userdto;
import com.example.distributed_lovable.CommonLib.common_lib.enums.ProjectRole;
import com.example.distributed_lovable.CommonLib.common_lib.error.ResourceNotFoundException;
import com.example.distributed_lovable.CommonLib.common_lib.security.JwtService;
import com.example.distributed_lovable.WorkspaceService.workspace_service.client.AccountClient;
import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.member.InviteMemberRequest;
import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.member.MemberResponse;
import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.member.UpdateMemberRoleRequest;
import com.example.distributed_lovable.WorkspaceService.workspace_service.entity.Project;
import com.example.distributed_lovable.WorkspaceService.workspace_service.entity.ProjectMember;
import com.example.distributed_lovable.WorkspaceService.workspace_service.entity.ProjectMemberId;
import com.example.distributed_lovable.WorkspaceService.workspace_service.mapper.MemberResponseMapper;
import com.example.distributed_lovable.WorkspaceService.workspace_service.repository.MemberResponseRepository;
import com.example.distributed_lovable.WorkspaceService.workspace_service.repository.ProjectRepository;
import com.example.distributed_lovable.WorkspaceService.workspace_service.service.ProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ProjectMemberServiceImpl implements ProjectMemberService
{
    ProjectRepository projectRepository;
    MemberResponseRepository memberResponseRepository;
    MemberResponseMapper memberResponseMapper;
    JwtService jwtService;
    AccountClient accountClient;

    @Override
    @PreAuthorize("@security.canViewMembersProject(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId)
    {
        Long userId=jwtService.getCurrentUser();
        Project project=projectRepository.findProjectByUserIdAndProjectId(userId,projectId).orElseThrow();
        List<MemberResponse> lstMemberResponse=new ArrayList<>();
        List<ProjectMember> projectMembers=memberResponseRepository.findAllProjctMemberById(projectId);
        List<MemberResponse> lsttemp=memberResponseMapper.toMemberResponse(projectMembers);
        lstMemberResponse.addAll(lsttemp);
        return lstMemberResponse;
    }

    @Override
    @PreAuthorize("@security.canManageMembersProject(#projectId)")
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request)
    {
        Long userId=jwtService.getCurrentUser();
        Project project= projectRepository.findProjectByUserIdAndProjectId(userId,projectId).orElseThrow(()->new RuntimeException("dsvdsfadscas"));
        Userdto invitee = accountClient.getUserByEmail(request.username());;
        ProjectMemberId projectMemberId1=new ProjectMemberId(project.getId(),userId);
        ProjectMember projectMember1=memberResponseRepository.findById(projectMemberId1).orElseThrow(()->new ResourceNotFoundException("No Such Records Are Available"));
        if(!projectMember1.getRole().equals(ProjectRole.OWNER))
        {
            throw new RuntimeException("Owner should not be invitee");
        }
        ProjectMemberId projectMemberId=new ProjectMemberId(projectId,invitee.id());
        if(memberResponseRepository.existsById(projectMemberId)){
            throw new RuntimeException("Memeber is already in project....");
        }
        ProjectMember projectMember=ProjectMember.builder()
                                     .id(projectMemberId)
                                     .project(project)
                                     .role(request.role())
                                     .invitedAt(Instant.now())
                                     .build();
        memberResponseRepository.save(projectMember);
        return memberResponseMapper.toMemberResponse(projectMember);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request)
    {

        List<ProjectMember> projectMemberList=memberResponseRepository.findAllProjctMemberById(projectId);
        if(!projectMemberList.stream().map(pm->pm.getId().getUserId()).toList().contains(memberId))
        {
            throw new RuntimeException("Member is not part of this project...");
        }
        ProjectMember updatableMember=projectMemberList.stream().filter(pm->pm.getId().getUserId()==memberId).findAny().get();
        updatableMember.setRole(ProjectRole.VIEWER);
        memberResponseRepository.save(updatableMember);
        return memberResponseMapper.toMemberResponse(updatableMember);
    }

    @Override
    public void deleteProjectMember(Long projectId, Long memberId)
    {
        List<ProjectMember> projectMemberList=memberResponseRepository.findAllProjctMemberById(projectId);
        if(!projectMemberList.stream().map(pm->pm.getId().getUserId()).toList().contains(memberId))
        {
            throw new RuntimeException("Member is not part of this project...");
        }
        ProjectMember deletedMember=projectMemberList.stream().filter(pm->pm.getId().getUserId()==memberId).findAny().get();
        memberResponseRepository.delete(deletedMember);
    }
}
