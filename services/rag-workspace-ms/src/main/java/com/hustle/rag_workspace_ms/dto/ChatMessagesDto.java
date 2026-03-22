package com.hustle.rag_workspace_ms.dto;


import com.hustle.rag_workspace_ms.model.entity.Message;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record ChatMessagesDto(

        UUID id,

        UUID workspaceId,

        List<Message> messages
) {
}
