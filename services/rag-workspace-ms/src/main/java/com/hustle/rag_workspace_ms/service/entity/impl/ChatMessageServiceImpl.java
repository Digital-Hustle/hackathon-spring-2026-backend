package com.hustle.rag_workspace_ms.service.entity.impl;

import com.hustle.rag_workspace_ms.factory.ChatMessageFactory;
import com.hustle.rag_workspace_ms.model.entity.Message;
import com.hustle.rag_workspace_ms.repository.ChatMessageRepository;
import com.hustle.rag_workspace_ms.service.entity.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public Page<Message> getByChatId(UUID workspaceId, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return chatMessageRepository.findAllByWorkspaceId(workspaceId, sortedPageable);
    }

    @Override
    public Message sendNew(UUID workspaceId, String content) {
        Message message = ChatMessageFactory.newProcessingPhotoMetaInfo(workspaceId, content);

        return chatMessageRepository.save(message);
    }
}
