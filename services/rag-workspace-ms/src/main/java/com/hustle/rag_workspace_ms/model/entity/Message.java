package com.hustle.rag_workspace_ms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "messages", schema = "rag")
public class Message {

    @Id
    private UUID id;

    @Column(name = "content")
    private String content;

    @Column(name = "owned_by_user")
    private Boolean ownedByUser;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private UUID workspaceId;
}
