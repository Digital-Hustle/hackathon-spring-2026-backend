package com.hustle.rag_workspace_ms.controller.impl;

import com.hustle.rag_workspace_ms.controller.ChatController;
import com.hustle.rag_workspace_ms.dto.response.ChatRs;
import com.hustle.rag_workspace_ms.mapper.ChatMessageMapper;
import com.hustle.rag_workspace_ms.model.entity.Message;
import com.hustle.rag_workspace_ms.service.entity.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatControllerImpl implements ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public ChatRs getChats(UUID workspaceId, Pageable pageable) {
        Page<Message> messages = chatMessageService.getByChatId(workspaceId, pageable);

        return ChatRs.builder()
                .messages(chatMessageMapper.convert(messages))
                .build();
    }
}
