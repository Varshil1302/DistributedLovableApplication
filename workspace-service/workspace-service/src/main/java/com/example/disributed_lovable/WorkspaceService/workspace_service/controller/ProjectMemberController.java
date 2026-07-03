package com.example.disributed_lovable.WorkspaceService.workspace_service.controller;


import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.member.InviteMemberRequest;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.member.MemberResponse;
import com.example.disributed_lovable.WorkspaceService.workspace_service.dto.member.UpdateMemberRoleRequest;
import com.example.disributed_lovable.WorkspaceService.workspace_service.service.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{id}/members")
@RequiredArgsConstructor
public class ProjectMemberController
{

    private final ProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getProjectMembers(@PathVariable("id") Long projectId) {

        return ResponseEntity.ok(projectMemberService.getProjectMembers(projectId));
    }

    @PostMapping
    public ResponseEntity<MemberResponse> inviteMember(
            @PathVariable("id") Long projectId,
            @RequestBody InviteMemberRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                projectMemberService.inviteMember(projectId, request)
        );
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(
            @PathVariable("id") Long projectId,
            @PathVariable Long memberId,
            @RequestBody @Valid UpdateMemberRoleRequest request
    ) {

        return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId, memberId, request));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMemberRole(
            @PathVariable("id") Long projectId,
            @PathVariable Long memberId
    ) {

        projectMemberService.deleteProjectMember(projectId, memberId);
        return ResponseEntity.noContent().build();
    }

}
