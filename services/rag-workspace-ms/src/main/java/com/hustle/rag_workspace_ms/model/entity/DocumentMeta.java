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

    @Column(name = "originalName", nullable = false)
    private String originalName;

    @Column(name = "contentType", nullable = false)
    private String contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "extension", nullable = false)
    private FileExtension extension;

    @Column(name = "size", nullable = false)
    private Long size;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FileProcessingStatus status;

    @Column(name = "errorMessage")
    private String errorMessage;

    @Column(name = "workspaceId", nullable = false)
    private UUID workspaceId;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}
