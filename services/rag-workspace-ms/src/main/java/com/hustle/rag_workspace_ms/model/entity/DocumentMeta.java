package com.hustle.rag_workspace_ms.model.entity;

import com.hustle.rag_workspace_ms.enums.FileExtension;
import com.hustle.rag_workspace_ms.enums.FileProcessingStatus;
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
@Table(name = "documents_meta", schema = "rag")
public class DocumentMeta {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String contentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileExtension extension;

    @Column(nullable = false)
    private Long size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileProcessingStatus status;

    @Column
    private String errorMessage;

    @Column(name = "active")
    private Boolean isActive;

    @Column(nullable = false)
    private UUID workspaceId;

    @Column
    private LocalDateTime createdAt;
}
