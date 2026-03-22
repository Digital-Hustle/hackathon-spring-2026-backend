package com.hustle.rag_workspace_ms.factory;

import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.model.entity.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChatMessageFactory {

    public static Message newProcessingPhotoMetaInfo(UUID workspaceId, String content) {
        return Message.builder()
                .id(UUID.randomUUID())
                .ownedByUser(true)
                .content(content)
                .workspaceId(workspaceId)
                .createdAt(LocalDateTime.now())
                .build();

    }
}
