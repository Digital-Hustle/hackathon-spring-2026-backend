package com.hustle.rag_workspace_ms.controller;

import com.hustle.rag_workspace_ms.dto.response.ChatRs;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

// TODO
//  2 ручки: на создание сообщение и получение всех сообщений по workspaceId с пагинацией
@RequestMapping("/api/v1/workspaces/{workspaceId}/chat")
public interface ChatController {

    @GetMapping
    ChatRs getChats(@PathVariable UUID workspaceId, Pageable pageable);
}
