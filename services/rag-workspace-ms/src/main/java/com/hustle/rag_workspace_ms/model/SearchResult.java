package com.hustle.rag_workspace_ms.model;

import java.util.Map;
import java.util.UUID;

public record SearchResult(

        UUID id,

        String content,

        Map<String, Object> metadata,

        double score,      // схожесть (0-1)

        int rank           // позиция после rerank
) {}