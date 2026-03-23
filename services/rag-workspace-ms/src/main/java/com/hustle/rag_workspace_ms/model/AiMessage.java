package com.hustle.rag_workspace_ms.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record AiMessage(

        String role,

        String content
) {
}
