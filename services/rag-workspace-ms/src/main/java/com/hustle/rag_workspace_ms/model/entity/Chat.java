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
@Table(name = "chats", schema = "rag")
public class Chat {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID workspaceId;

    @Column
    private LocalDateTime createdAt;
}
