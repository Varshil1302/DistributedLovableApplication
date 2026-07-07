package com.example.distributed_lovable.IntelligenceService.repository;


import com.example.distributed_lovable.IntelligenceService.entity.ChatSession;
import com.example.distributed_lovable.IntelligenceService.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId>
{

}
