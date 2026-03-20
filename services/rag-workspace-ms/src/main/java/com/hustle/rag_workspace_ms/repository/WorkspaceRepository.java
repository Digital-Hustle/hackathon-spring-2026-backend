package com.hustle.rag_workspace_ms.repository;

import com.hustle.rag_workspace_ms.model.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
}
