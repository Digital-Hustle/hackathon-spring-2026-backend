package com.hustle.rag_workspace_ms.controller.impl;

import com.hustle.rag_workspace_ms.controller.WorkspaceController;
import com.hustle.rag_workspace_ms.dto.WorkspaceDto;
import com.hustle.rag_workspace_ms.dto.request.UpdateWorkspaceFavouriteRq;
import com.hustle.rag_workspace_ms.dto.request.UpdateWorkspaceRq;
import com.hustle.rag_workspace_ms.dto.response.WorkspacesRs;
import com.hustle.rag_workspace_ms.mapper.WorkspaceMapper;
import com.hustle.rag_workspace_ms.model.entity.Workspace;
import com.hustle.rag_workspace_ms.service.entity.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WorkspaceControllerImpl implements WorkspaceController {

    private final WorkspaceService workspaceService;
    private final WorkspaceMapper workSpaceMapper;

    @Override
    public WorkspacesRs getAll(Pageable pageable) {
        Page<Workspace> workspacePage = workspaceService.getAll(pageable);

        return WorkspacesRs.builder()
                .workspacesPage(workSpaceMapper.convert(workspacePage))
                .build();
    }

    @Override
    public WorkspaceDto create() {
        Workspace workspace = workspaceService.createDefault();

        return workSpaceMapper.convert(workspace);
    }

    @Override
    public WorkspaceDto update(UUID id, UpdateWorkspaceRq updateWorkspaceRq) {
        Workspace workspace = workSpaceMapper.convert(updateWorkspaceRq);
        Workspace updatedWorkspace = workspaceService.update(id, workspace);

        return workSpaceMapper.convert(updatedWorkspace);
    }

    @Override
    public void updateFavourite(UUID id, UpdateWorkspaceFavouriteRq updateWorkspaceFavouriteRq) {
        Boolean isFavourite = updateWorkspaceFavouriteRq.isFavourite();
        workspaceService.updateFavourite(id, isFavourite);
    }
}
