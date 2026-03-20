package com.hustle.rag_workspace_ms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DocumentMetaRs(

        UUID id,

        String originalName,

        String contentType,

        Long size,

        String status,

        String errorMessage,

        UUID workspaceId,

        LocalDateTime createdAt
) {
}
