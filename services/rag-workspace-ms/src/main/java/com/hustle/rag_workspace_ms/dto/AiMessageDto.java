package com.hustle.rag_workspace_ms.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record AiMessageDto(

        String role,

        String content
) {
}