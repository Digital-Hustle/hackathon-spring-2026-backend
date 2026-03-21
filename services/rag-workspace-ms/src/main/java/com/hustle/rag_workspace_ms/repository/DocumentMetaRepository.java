package com.hustle.rag_workspace_ms.repository;

import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DocumentMetaRepository extends JpaRepository<DocumentMeta, UUID> {

    List<DocumentMeta> findAllByWorkspaceId(UUID workspaceId);

    @Modifying
    @Query("UPDATE DocumentMeta dm SET dm.isActive = :isActive WHERE dm.id = :id")
    void updateActivity(@Param("id") UUID id, @Param("isActive") Boolean isActive);

    boolean existsByWorkspaceId(UUID workspaceId);
}
