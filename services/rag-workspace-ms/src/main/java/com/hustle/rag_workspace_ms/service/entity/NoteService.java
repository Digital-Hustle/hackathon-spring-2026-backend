package com.hustle.rag_workspace_ms.service.entity;

import com.hustle.rag_workspace_ms.dto.request.CreateNoteRq;
import com.hustle.rag_workspace_ms.dto.response.NotePreviewRs;
import com.hustle.rag_workspace_ms.dto.response.NoteRs;
import com.hustle.rag_workspace_ms.dto.response.UpdateNoteRq;
import com.hustle.rag_workspace_ms.model.entity.Note;

import java.util.List;
import java.util.UUID;


public interface NoteService {

    NoteRs create(CreateNoteRq rq, UUID workspaceId);

    NoteRs getById(UUID id);

    List<NotePreviewRs> getByWorkspaceId(UUID workspaceId);

    NoteRs update(UUID id, UpdateNoteRq rq);

    void delete(UUID id);
}
