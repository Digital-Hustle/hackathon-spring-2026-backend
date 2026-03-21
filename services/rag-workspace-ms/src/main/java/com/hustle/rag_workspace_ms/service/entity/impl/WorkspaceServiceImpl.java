package com.hustle.rag_workspace_ms.service.entity.impl;

import com.hustle.rag_workspace_ms.factory.WorkspaceFactory;
import com.hustle.rag_workspace_ms.model.entity.Workspace;
import com.hustle.rag_workspace_ms.repository.WorkspaceRepository;
import com.hustle.rag_workspace_ms.service.entity.WorkspaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Override
    public Page<Workspace> getAll(Pageable pageable) {
        return workspaceRepository.findAll(pageable);
    }

    @Override
    public Workspace createDefault() {
        Workspace workspace = WorkspaceFactory.newDefaultWorkspace(UUID.randomUUID());

        return workspaceRepository.save(workspace);
    }

    @Override
    public Workspace update(UUID id, Workspace workspace) {
        return workspaceRepository.save(
                workspace.toBuilder()
                        .id(id)
                        .build()
        );
    }

    @Transactional
    @Override
    public void updateFavourite(UUID id, Boolean isFavourite) {
        workspaceRepository.updateFavouriteById(id, isFavourite);
    }
}
