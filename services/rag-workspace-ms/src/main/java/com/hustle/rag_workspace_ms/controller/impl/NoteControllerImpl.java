package com.hustle.rag_workspace_ms.controller.impl;

import com.hustle.rag_workspace_ms.controller.NoteController;
import com.hustle.rag_workspace_ms.dto.request.CreateNoteRq;
import com.hustle.rag_workspace_ms.dto.response.NotePreviewRs;
import com.hustle.rag_workspace_ms.dto.response.NoteRs;
import com.hustle.rag_workspace_ms.dto.response.UpdateNoteRq;
import com.hustle.rag_workspace_ms.service.entity.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NoteControllerImpl implements NoteController {

    private final NoteService noteService;

    @Override
    public NoteRs getNoteById(UUID id) {
        return noteService.getById(id);
    }

    @Override
    public List<NotePreviewRs> getNotesByWorkspaceId(UUID workspaceId) {
        return noteService.getByWorkspaceId(workspaceId);
    }

    @Override
    public NoteRs createNote(CreateNoteRq createNoteRq, @PathVariable("workspaceId") UUID workspaceId) {
        return noteService.create(createNoteRq, workspaceId);
    }

    @Override
    public NoteRs updateNoteById(UUID id, UpdateNoteRq updateNoteRq) {
        return noteService.update(id, updateNoteRq);
    }
}
