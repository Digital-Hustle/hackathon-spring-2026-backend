package com.hustle.rag_workspace_ms.model.entity;

import com.hustle.rag_workspace_ms.enums.WorkspaceCardType;
import jakarta.persistence.*;
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
@Table(name = "workspaces", schema = "rag")
public class Workspace {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkspaceCardType cardType;

    @Column
    private String icon;

    @Column
    private String type;

    // TODO проверить, что не такой же проблемы для файла и isActive
    @Column(name = "favourite")
    private Boolean isFavourite;

    @Column
    private LocalDateTime createdAt;
}
