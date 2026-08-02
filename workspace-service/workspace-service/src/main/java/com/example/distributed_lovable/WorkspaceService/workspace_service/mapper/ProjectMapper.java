package com.example.distributed_lovable.WorkspaceService.workspace_service.mapper;


import com.example.distributed_lovable.CommonLib.common_lib.enums.ProjectRole;
import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.project.ProjectResponse;
import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.project.ProjectSummaryResponse;
import com.example.distributed_lovable.WorkspaceService.workspace_service.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper
{
     //@Mapping(target = "owner.userId",source = "")
     ProjectResponse toProjectResponse(Project project);

     @Mapping(target = "role", source = "projectRole")
     ProjectSummaryResponse toProjectSummaryResponse(Project project, ProjectRole projectRole);

     List<ProjectSummaryResponse> toProjectSummury(List<Project> projectList);
}
