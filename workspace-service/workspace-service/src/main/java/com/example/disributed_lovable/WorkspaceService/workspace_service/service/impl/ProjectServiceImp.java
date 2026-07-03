package com.example.disributed_lovable.WorkspaceService.workspace_service.service.impl;

import com.example.disributed_lovable.CommonLib.common_lib.enums.ProjectRole;
import com.example.disributed_lovable.CommonLib.common_lib.error.BadRequestException;
import com.example.disributed_lovable.CommonLib.common_lib.error.ResourceNotFoundException;
import com.example.disributed_lovable.CommonLib.common_lib.security.JwtService;
import com.example.disributed_lovable.WorkspaceService.workspace_service.client.AccountClient;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.ProjectRequest;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.ProjectResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.ProjectSummaryResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.entity.Project;
import com.example.disributed_lovable.WorkspaceService.workspace_service.entity.ProjectMember;
import com.example.disributed_lovable.WorkspaceService.workspace_service.entity.ProjectMemberId;
import com.example.disributed_lovable.WorkspaceService.workspace_service.mapper.ProjectMapper;
import com.example.disributed_lovable.WorkspaceService.workspace_service.repository.MemberResponseRepository;
import com.example.disributed_lovable.WorkspaceService.workspace_service.repository.ProjectRepository;
import com.example.disributed_lovable.WorkspaceService.workspace_service.service.ProjectService;
import com.example.disributed_lovable.WorkspaceService.workspace_service.service.ProjectTemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImp implements ProjectService
{

    ProjectRepository projectRepository;
    MemberResponseRepository memberResponseRepository;
    ProjectMapper projectMapper;
    JwtService jwtService;
    ProjectTemplateService projectTemplateService;
    AccountClient accountClient;

    @Override
    public List<ProjectSummaryResponse> getUserProjects()
    {
        Long userId=jwtService.getCurrentUser();
        List<ProjectRepository.ProjectWithRole> projectList=projectRepository.findAllByUserId(userId);
        return projectList.stream().map(pwr->projectMapper.toProjectSummaryResponse(pwr.getProject(),pwr.getUserrole())).toList();
    }

    @Override
    @PreAuthorize("@security.canViewProject(#id)")
    public ProjectSummaryResponse getProjectDetailsById(Long id)
    {
        Long userId=jwtService.getCurrentUser();
        var projectRole=projectRepository.findAccessibleProjectByIdWithRole(id,userId).orElseThrow(()->new BadRequestException(""));
        log.info("Role of user:: "+projectRole.getUserrole());
        log.info("Name Of The Project::"+projectRole.getProject().getName());
        return projectMapper.toProjectSummaryResponse(projectRole.getProject(),projectRole.getUserrole());
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request)
    {
        Long userId=jwtService.getCurrentUser();
       // User user=userRepository.findById(userId).orElseThrow();
        Project project=Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();


        projectRepository.save(project);

        ProjectMemberId projectMemberId=new ProjectMemberId(project.getId(),userId);
        ProjectMember projectMember= ProjectMember.builder()
                                      .id(projectMemberId)
                                      .project(project)
                                      .role(ProjectRole.OWNER)
                                      .invitedAt(Instant.now())
                                      .acceptedAt(Instant.now())
                                      .build();
        memberResponseRepository.save(projectMember);
        projectTemplateService.initializeProjectFromTemplate(project.getId());
        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canEditProject(#id)")
    public ProjectResponse updateProject(Long id, ProjectRequest request)
    {
        Long userId=jwtService.getCurrentUser();
        Project project=projectRepository.findById(id).orElseThrow();
        ProjectMemberId projectMemberId=new ProjectMemberId(project.getId(),userId);
        ProjectMember projectMember=memberResponseRepository.findById(projectMemberId).orElseThrow(()->new ResourceNotFoundException("No Such Records Are Available"));
        if(projectMember.getRole().equals(ProjectRole.VIEWER))
        {
            throw new RuntimeException("Not Allowed To Update");
        }
        project.setName(request.name());
        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canDeleteProject(#id)")
    public void softDelete(Long id)
    {
        Long userId=jwtService.getCurrentUser();
        Project project=projectRepository.findById(id).orElseThrow();
        ProjectMemberId projectMemberId=new ProjectMemberId(project.getId(),userId);
        ProjectMember projectMember=memberResponseRepository.findById(projectMemberId).orElseThrow(()->new ResourceNotFoundException("No Such Records Are Available"));
        if(projectMember.getRole().equals(ProjectRole.VIEWER))
        {
            throw new RuntimeException("Not Allowed To Delete");
        }
        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }
}
