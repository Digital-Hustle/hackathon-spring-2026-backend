package com.hustle.rag_workspace_ms.service.entity;

import com.hustle.rag_workspace_ms.model.entity.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WorkspaceService {

    Page<Workspace> getAll(Pageable pageable);

    Workspace getById(UUID id);

    Workspace createDefault();

    Workspace update(UUID id, Workspace workspace);

    void updateFavourite(UUID id, Boolean isFavourite);
}
