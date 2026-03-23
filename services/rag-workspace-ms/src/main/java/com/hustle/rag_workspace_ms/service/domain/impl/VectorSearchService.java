package com.hustle.rag_workspace_ms.service.domain.impl;

import com.hustle.rag_workspace_ms.model.SearchResult;
import com.hustle.rag_workspace_ms.repository.VectorStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final VectorStoreRepository vectorStoreRepository;

    public List<SearchResult> search(UUID workspaceId, float[] queryVector, int topK) {
        log.debug("Vector search: workspace={}, topK={}, vectorSize={}", 
                  workspaceId, topK, queryVector.length);
        
        List<SearchResult> results = vectorStoreRepository.searchSimilar(
                workspaceId,
                queryVector,
                topK
        );
        
        log.info("Found {} similar chunks", results.size());
        return results;
    }

    public List<SearchResult> searchByDocument(
            UUID workspaceId, 
            UUID documentId, 
            float[] queryVector, 
            int topK
    ) {
        return vectorStoreRepository.searchSimilarByDocument(
                workspaceId,
                documentId,
                queryVector,
                topK
        );
    }
}