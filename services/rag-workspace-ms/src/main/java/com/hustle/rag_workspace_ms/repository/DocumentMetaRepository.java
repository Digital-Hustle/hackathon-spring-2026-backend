package com.hustle.rag_workspace_ms.repository;

import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentMetaRepository extends JpaRepository<DocumentMeta, UUID> {

    boolean existsByWorkspaceId(UUID workspaceId);

    Optional<DocumentMeta> findByWorkspaceId(UUID ownerId);
}
