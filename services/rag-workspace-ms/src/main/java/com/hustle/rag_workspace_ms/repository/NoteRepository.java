package com.hustle.rag_workspace_ms.repository;

import com.hustle.rag_workspace_ms.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findAllByWorkspaceId(UUID workspaceId);
}
