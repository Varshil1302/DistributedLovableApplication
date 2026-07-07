package com.example.distributed_lovable.IntelligenceService.repository;


import com.example.distributed_lovable.IntelligenceService.entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatEventRepository extends JpaRepository<ChatEvent,Long>
{

}
