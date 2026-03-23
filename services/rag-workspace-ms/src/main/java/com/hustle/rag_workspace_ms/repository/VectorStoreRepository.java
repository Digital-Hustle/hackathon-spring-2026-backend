package com.hustle.rag_workspace_ms.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hustle.rag_workspace_ms.model.SearchResult;
import com.hustle.rag_workspace_ms.model.VectorStoreChunk;
import com.hustle.rag_workspace_ms.utils.JsonbUtils;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Slf4j
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

    public List<SearchResult> searchSimilar(UUID workspaceId, float[] queryVector, int topK) {
        String vectorStr = vectorToString(queryVector);


        String sql = """
                    SELECT 
                        id,
                        content,
                        metadata,
                        1 - (embedding <=> '%s'::vector) as similarity_score
                    FROM vector_store
                    WHERE workspace_id = ?
                    ORDER BY embedding <=> '%s'::vector
                    LIMIT ?
                """.formatted(vectorStr, vectorStr);

        return jdbcTemplate.query(sql, searchResultRowMapper(), workspaceId, topK);
    }

    public List<SearchResult> searchSimilarByDocument(
            UUID workspaceId, UUID documentId, float[] queryVector, int topK
    ) {
        String vectorStr = vectorToString(queryVector);

        String sql = """
                    SELECT 
                        id,
                        content,
                        metadata,
                        1 - (embedding <=> '%s'::vector) as similarity_score
                    FROM vector_store
                    WHERE workspace_id = ? AND (metadata->>'documentId')::uuid = ?
                    ORDER BY embedding <=> '%s'::vector
                    LIMIT ?
                """.formatted(vectorStr, vectorStr);

        return jdbcTemplate.query(sql, searchResultRowMapper(),
                workspaceId, documentId, topK);
    }

    // ✅ Исправлено: Locale.US для точки вместо запятой
    private String vectorToString(float[] vector) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < vector.length; i++) {
            if (i > 0) sb.append(",");
            // ✅ Locale.US гарантирует точку как разделитель
            sb.append(String.format(Locale.US, "%.8f", vector[i]));
        }
        sb.append("]");
        return sb.toString();
    }

    private RowMapper<SearchResult> searchResultRowMapper() {
        return (ResultSet rs, int rowNum) -> {
            try {
                String metadataJson = rs.getString("metadata");
                if (metadataJson == null || metadataJson.isEmpty()) {
                    metadataJson = "{}";
                }

                @SuppressWarnings("unchecked")
                Map<String, Object> metadata = objectMapper.readValue(metadataJson, Map.class);

                double score = rs.getDouble("similarity_score");
                if (Double.isNaN(score) || Double.isInfinite(score)) {
                    score = 0.0;
                }

                return new SearchResult(
                        rs.getObject("id", UUID.class),
                        rs.getString("content"),
                        metadata,
                        score,
                        rowNum + 1
                );

            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse meta " + e.getMessage(), e);
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage(), e);
            }
        };
    }
}
