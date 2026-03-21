package com.hustle.rag_workspace_ms.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record DocumentText(

        UUID id,

        String content
) {
}
