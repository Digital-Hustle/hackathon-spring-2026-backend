package com.hustle.rag_workspace_ms.service.domain.impl;

import com.hustle.rag_workspace_ms.model.RagQueryRequest;
import com.hustle.rag_workspace_ms.model.RagQueryResponse;
import com.hustle.rag_workspace_ms.model.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagService {

    private final EmbeddingModel embeddingModel;
    private final VectorSearchService vectorSearchService;
    private final LlmService llmService;

    public RagQueryResponse query(RagQueryRequest request) {
        log.info("RAG query for workspace: {}, question: {}", 
                 request.workspaceId(), request.question());
        
        float[] queryVector = embeddingModel.embed(request.question());
        
        List<SearchResult> searchResults = vectorSearchService.search(
                request.workspaceId(),
                queryVector,
                request.topK()
        );

        String answer = llmService.generateAnswer(
                request.question(),
                searchResults
        );
        
        return new RagQueryResponse(
                answer,
                searchResults,
                searchResults.size()
        );
    }

    public List<SearchResult> searchOnly(UUID workspaceId, String question, int topK) {
        float[] queryVector = embeddingModel.embed(question);
        return vectorSearchService.search(workspaceId, queryVector, topK);
    }
}