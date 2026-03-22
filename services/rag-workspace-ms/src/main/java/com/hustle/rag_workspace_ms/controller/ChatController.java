package com.hustle.rag_workspace_ms.controller;

import com.hustle.rag_workspace_ms.dto.MessageDto;
import com.hustle.rag_workspace_ms.dto.request.SendMessageRq;
import com.hustle.rag_workspace_ms.dto.response.ChatRs;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/workspaces/{workspaceId}/chat")
public interface ChatController {

    @GetMapping
    ChatRs getChats(@PathVariable UUID workspaceId, Pageable pageable);

    @PostMapping
    MessageDto sendMessage(@PathVariable UUID workspaceId, @RequestBody SendMessageRq sendMessageRq);
}
