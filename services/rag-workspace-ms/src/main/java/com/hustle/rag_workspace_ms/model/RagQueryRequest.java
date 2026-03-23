package com.hustle.rag_workspace_ms.model;

import java.util.UUID;

public record RagQueryRequest(

        UUID workspaceId,

        String question,

        int topK,

        int topN
) {
    public RagQueryRequest {
        if (topK <= 0) {
            topK = 10;
        }

        if (topN <= 0) {
            topN = 3;
        }

        if (topN > topK) {
            topN = topK;
        }
    }
}