package com.hustle.rag_workspace_ms.dto.request;

import com.hustle.rag_workspace_ms.enums.WorkspaceCardType;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateWorkspaceRq(

        UUID id,

        String title,

        WorkspaceCardType cardType,

        String icon,

        String type,

        String isFavourite,

        LocalDateTime createdAt
) {
}
