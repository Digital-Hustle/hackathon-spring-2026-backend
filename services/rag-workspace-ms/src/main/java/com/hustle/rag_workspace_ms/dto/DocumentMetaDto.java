package com.hustle.rag_workspace_ms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DocumentMetaDto(

        UUID id,

        String originalName,

        String contentType,

        Long size,

        String status,

        String errorMessage,

        Boolean isActive,

        UUID workspaceId,

        LocalDateTime createdAt
) {
}
