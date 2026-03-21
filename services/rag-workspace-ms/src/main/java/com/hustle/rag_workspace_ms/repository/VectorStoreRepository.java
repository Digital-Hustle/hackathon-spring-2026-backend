package com.hustle.rag_workspace_ms.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hustle.rag_workspace_ms.utils.JsonbUtils;
import com.hustle.rag_workspace_ms.model.VectorStoreChunk;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

// TODO добавить поиск по title для workspace
//  должна быть пагинация

@Repository
@RequiredArgsConstructor
public class VectorStoreRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EmbeddingModel embeddingModel;

    private final ObjectMapper objectMapper; // внедряем

    public void saveChunk(UUID id, UUID workspaceId, String content, Map<String, Object> metadata, float[] vector) {
        try {
            String metadataJson = objectMapper.writeValueAsString(metadata);
            String sql = """
                INSERT INTO vector_store (id, workspace_id, content, metadata, embedding)
                VALUES (?, ?, ?, ?::jsonb, ?)
            """;
            jdbcTemplate.update(sql, id, workspaceId, content, metadataJson, vector);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize metadata", e);
        }
    }

    public List<VectorStoreChunk> findSimilar(String queryText, UUID workspaceId, int topK) {
        float[] vector = embeddingModel.embed(queryText);

        String sql = """
                    SELECT id, content, metadata, (embedding <=> ?) AS distance
                    FROM vector_store
                    WHERE workspace_id = ?
                    ORDER BY embedding <=> ?
                    LIMIT ?
                """;

        return jdbcTemplate.query(sql, new Object[]{vector, workspaceId, vector, topK},
                (rs, rowNum) -> {
                    UUID id = rs.getObject("id", UUID.class);
                    String content = rs.getString("content");
                    String metadataJson = rs.getString("metadata");
                    Map<String, Object> metadata = JsonbUtils.fromJson(metadataJson);
                    double distance = rs.getDouble("distance");
                    return new VectorStoreChunk(id, content, metadata, distance);
                });
    }
}
