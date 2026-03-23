package com.hustle.rag_workspace_ms.repository;

import com.hustle.rag_workspace_ms.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<Message, UUID> {

    List<Message> findAllByWorkspaceId(UUID workspaceId);

    Page<Message> findAllByWorkspaceId(UUID workspaceId, Pageable pageable);
}
