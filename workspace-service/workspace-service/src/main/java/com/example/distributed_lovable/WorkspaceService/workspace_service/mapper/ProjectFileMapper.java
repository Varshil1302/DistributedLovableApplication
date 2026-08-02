
package com.example.distributed_lovable.WorkspaceService.workspace_service.mapper;


import com.example.distributed_lovable.CommonLib.common_lib.dto.FileNode;
import com.example.distributed_lovable.WorkspaceService.workspace_service.entity.ProjectFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectFileMapper
{
    List<FileNode> getFromProjectFile(List<ProjectFile> projectFiles);
}
