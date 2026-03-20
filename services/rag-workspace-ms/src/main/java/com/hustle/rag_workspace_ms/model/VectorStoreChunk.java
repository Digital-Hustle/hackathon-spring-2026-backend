package com.hustle.rag_workspace_ms.model;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
public record VectorStoreChunk(

        UUID id,

        String content,

        Map<String, Object> metadata,

        double distance
) {
}
