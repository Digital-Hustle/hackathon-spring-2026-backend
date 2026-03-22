package com.hustle.rag_workspace_ms.repository;

import com.hustle.rag_workspace_ms.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    Optional<Chat> findByWorkspaceId(UUID uuid);
}
