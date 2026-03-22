package com.hustle.rag_workspace_ms.repository;

import com.hustle.rag_workspace_ms.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<Message, UUID> {

    Page<Message> findAllByWorkspaceId(UUID chatId, Pageable pageable);
}
