package com.hustle.rag_workspace_ms.service.entity;

import com.hustle.rag_workspace_ms.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ChatMessageService {

    Page<Message> getByChatId(UUID workspaceId, Pageable pageable);

    Message sendNew(UUID workspaceId, String content);
}
