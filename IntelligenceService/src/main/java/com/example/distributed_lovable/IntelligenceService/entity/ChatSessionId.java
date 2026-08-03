package com.example.distributed_lovable.IntelligenceService.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
public class ChatSessionId implements Serializable
{
     Long projectId;
     Long userId;
}
