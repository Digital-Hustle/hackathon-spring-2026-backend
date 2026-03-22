package com.hustle.rag_workspace_ms.service.entity.impl;

import com.hustle.rag_workspace_ms.model.entity.Message;
import com.hustle.rag_workspace_ms.repository.ChatMessageRepository;
import com.hustle.rag_workspace_ms.repository.ChatRepository;
import com.hustle.rag_workspace_ms.service.entity.ChatMessageService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public Page<Message> getByChatId(UUID workspaceId, Pageable pageable) {

        return chatMessageRepository.findAllByWorkspaceId(workspaceId, pageable);
    }
}
