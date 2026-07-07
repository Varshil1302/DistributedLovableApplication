package com.example.distributed_lovable.IntelligenceService.repository;


import com.example.distributed_lovable.IntelligenceService.entity.ChatMessage;
import com.example.distributed_lovable.IntelligenceService.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long>
{
    @Query("""
            select DISTINCT m from ChatMessage m 
            LEFT JOIN FETCH m.chatEventList e
            WHERE m.chatSession = :chatSession
            ORDER BY m.createdAt ASC, e.sequenceOrder ASC
            """)
    List<ChatMessage> findByChatSession(ChatSession chatSession);
}
