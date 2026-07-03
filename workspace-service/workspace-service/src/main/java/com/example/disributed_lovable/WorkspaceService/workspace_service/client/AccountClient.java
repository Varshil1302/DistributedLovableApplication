package com.example.disributed_lovable.WorkspaceService.workspace_service.client;

import com.example.disributed_lovable.CommonLib.common_lib.dto.Userdto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service" ,path = "/account")
public interface AccountClient
{
    @GetMapping("/internal/v1/user/{userId}")
    public Userdto getUserByUserId(@PathVariable Long userId);

    @GetMapping("/internal/v1/user/by-email")
    public Userdto getUserByEmail(@RequestParam String email);
}
