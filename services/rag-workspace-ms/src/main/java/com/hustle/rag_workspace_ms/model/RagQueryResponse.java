package com.hustle.rag_workspace_ms.model;

import java.util.List;

public record RagQueryResponse(

        String answer,

        List<SearchResult> sources,

        int totalFound
) {}