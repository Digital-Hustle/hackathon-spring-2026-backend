package com.hustle.rag_workspace_ms.service.entity.impl;

import com.hustle.rag_workspace_ms.dto.request.CreateNoteRq;
import com.hustle.rag_workspace_ms.dto.response.NotePreviewRs;
import com.hustle.rag_workspace_ms.dto.response.NoteRs;
import com.hustle.rag_workspace_ms.dto.response.UpdateNoteRq;
import com.hustle.rag_workspace_ms.mapper.NoteMapper;
import com.hustle.rag_workspace_ms.mapper.util.JsonNodeMapper;
import com.hustle.rag_workspace_ms.model.entity.Note;
import com.hustle.rag_workspace_ms.repository.NoteRepository;
import com.hustle.rag_workspace_ms.service.entity.NoteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final JsonNodeMapper jsonNodeMapper;

    @Override
    public NoteRs create(CreateNoteRq rq, UUID workspaceId) {
        Note note = noteMapper.toEntity(rq).toBuilder()
                .id(UUID.randomUUID())
                .workspaceId(workspaceId)
                .build();

        Note savedNote = noteRepository.save(note);

        return noteMapper.toDto(savedNote);
    }

    @Override
    public NoteRs getById(UUID id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found: " + id));

        return noteMapper.toDto(note);
    }

    @Override
    public List<NotePreviewRs> getByWorkspaceId(UUID workspaceId) {
        return noteRepository.findAllByWorkspaceId(workspaceId).stream()
                .map(noteMapper::toPreviewDto)
                .toList();
    }

    @Override
    public NoteRs update(UUID id, UpdateNoteRq rq) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found: " + id));

        Note updated = existing.toBuilder()
                .title(rq.title())
                .contentJson(jsonNodeMapper.jsonNodeToString(rq.contentJson()))
                .build();

        Note saved = noteRepository.save(updated);
        return noteMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found: " + id));

        noteRepository.delete(existing);
    }
}
