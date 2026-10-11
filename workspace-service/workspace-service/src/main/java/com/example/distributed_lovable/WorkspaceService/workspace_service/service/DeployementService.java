package com.example.distributed_lovable.WorkspaceService.workspace_service.service;

import com.example.distributed_lovable.WorkspaceService.workspace_service.dto.deploy.DeployementResponse;

public interface DeployementService
{
    DeployementResponse deploy(Long projectId);
}
