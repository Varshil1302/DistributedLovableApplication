package com.example.disributed_lovable.WorkspaceService.workspace_service.service;



import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.ProjectRequest;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.ProjectResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.project.ProjectSummaryResponse;

import java.util.List;

public interface ProjectService
{

    List<ProjectSummaryResponse> getUserProjects();

    ProjectSummaryResponse getProjectDetailsById(Long id);

     ProjectResponse createProject(ProjectRequest request);

     ProjectResponse updateProject(Long id, ProjectRequest request);

    void softDelete(Long id);
}
