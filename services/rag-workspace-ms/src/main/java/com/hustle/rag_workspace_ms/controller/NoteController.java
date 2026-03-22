package com.hustle.rag_workspace_ms.controller;

import com.hustle.rag_workspace_ms.dto.request.CreateNoteRq;
import com.hustle.rag_workspace_ms.dto.response.NotePreviewRs;
import com.hustle.rag_workspace_ms.dto.response.NoteRs;
import com.hustle.rag_workspace_ms.dto.response.UpdateNoteRq;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/workspaces/{workspaceId}/note")
public interface NoteController {
    @GetMapping("/{id}")
    NoteRs getNoteById(@PathVariable("id") UUID id);

    @GetMapping
    List<NotePreviewRs> getNotesByWorkspaceId(@PathVariable("workspaceId") UUID workspaceId);

    @PostMapping
    NoteRs createNote(@RequestBody CreateNoteRq createNoteRq, @PathVariable("workspaceId") UUID workspaceId);

    @PutMapping("/{id}")
    NoteRs updateNoteById(@PathVariable("id") UUID id, @RequestBody UpdateNoteRq updateNoteRq);
}
