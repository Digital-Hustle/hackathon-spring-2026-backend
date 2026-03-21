package com.hustle.rag_workspace_ms.dto;

import com.hustle.rag_workspace_ms.enums.WorkspaceCardType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record WorkspaceDto(

        UUID id,

        String title,

        WorkspaceCardType cardType,

        String icon,

        String type,

        boolean isFavorite,

        LocalDateTime createdAt
) {
}
