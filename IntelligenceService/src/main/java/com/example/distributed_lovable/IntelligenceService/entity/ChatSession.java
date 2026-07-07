package com.example.distributed_lovable.IntelligenceService.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "chat_sessions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString
public class ChatSession
{
    @EmbeddedId
    ChatSessionId chatSessionId;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;

    Instant deletedAt;
}
