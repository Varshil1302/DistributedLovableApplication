package com.example.distributed_lovable.IntelligenceService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatSessionId implements Serializable
{
     Long projectId;
     Long userId;
}
