package com.hustle.rag_workspace_ms.controller;

import com.hustle.rag_workspace_ms.dto.WorkspaceDto;
import com.hustle.rag_workspace_ms.dto.request.UpdateWorkspaceFavouriteRq;
import com.hustle.rag_workspace_ms.dto.request.UpdateWorkspaceRq;
import com.hustle.rag_workspace_ms.dto.response.WorkspacesRs;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/workspaces")
public interface WorkspaceController {

    @GetMapping
    WorkspacesRs getAll(Pageable pageable);

    @PostMapping
    WorkspaceDto create();

    @GetMapping("/{id}")
    WorkspaceDto getById(@PathVariable UUID id);

    @PutMapping("/{id}")
    WorkspaceDto update(@PathVariable UUID id, @RequestBody UpdateWorkspaceRq updateWorkspaceRq);

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateFavourite(@PathVariable UUID id, @RequestBody UpdateWorkspaceFavouriteRq updateWorkspaceFavouriteRq);
}
