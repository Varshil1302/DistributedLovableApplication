package com.example.disributed_lovable.WorkspaceService.workspace_service.repository;


import com.example.disributed_lovable.CommonLib.common_lib.enums.ProjectRole;
import com.example.disributed_lovable.WorkspaceService.workspace_service.entity.ProjectMember;
import com.example.disributed_lovable.WorkspaceService.workspace_service.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberResponseRepository extends JpaRepository<ProjectMember, ProjectMemberId>
{
     @Query("""
            SELECT pm FROM ProjectMember pm 
            where pm.project.id= :projectId
            ORDER BY pm.invitedAt DESC
             """)
     List<ProjectMember> findAllProjctMemberById(Long projectId);

    @Query("""
           SELECT pm.role from ProjectMember pm
            where pm.id.userId= :userId AND pm.id.projectId= :projectId 
            """)
    Optional<ProjectRole> findRoleByUserIdAndProjectId(@Param("userId") Long userId,
                                                       @Param("projectId") Long projectId);
}
