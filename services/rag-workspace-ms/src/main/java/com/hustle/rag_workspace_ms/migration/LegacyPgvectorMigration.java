package com.hustle.rag_workspace_ms.migration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hustle.rag_workspace_ms.repository.VectorStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegacyPgvectorMigration implements ApplicationRunner {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() { };
    private static final String HAS_LEGACY_TABLE_SQL =
            "SELECT to_regclass('rag.vector_store') IS NOT NULL";
    private static final String READ_LEGACY_CHUNKS_SQL = """
            SELECT id, workspace_id, content, metadata::text AS metadata, embedding::text AS embedding
            FROM rag.vector_store
            ORDER BY id
            """;

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final VectorStoreRepository vectorStoreRepository;

    @Override
    public void run(ApplicationArguments args) {
        Boolean legacyTableExists = jdbcTemplate.queryForObject(HAS_LEGACY_TABLE_SQL, Boolean.class);
        if (!Boolean.TRUE.equals(legacyTableExists)) {
            return;
        }

        AtomicLong migratedChunks = new AtomicLong();
        jdbcTemplate.query(READ_LEGACY_CHUNKS_SQL, (RowCallbackHandler) resultSet -> {
            migrateChunk(resultSet);
            migratedChunks.incrementAndGet();
        });

        jdbcTemplate.execute("DROP TABLE IF EXISTS rag.vector_store");
        jdbcTemplate.execute("DROP EXTENSION IF EXISTS vector");
        log.info("Migrated {} legacy pgvector chunks to Qdrant", migratedChunks.get());
    }

    private void migrateChunk(ResultSet resultSet) throws SQLException {
        UUID id = resultSet.getObject("id", UUID.class);
        UUID workspaceId = resultSet.getObject("workspace_id", UUID.class);
        String content = resultSet.getString("content");
        Map<String, Object> metadata = readMetadata(resultSet.getString("metadata"));
        float[] embedding = parseVector(resultSet.getString("embedding"));
        vectorStoreRepository.saveChunk(id, workspaceId, content, metadata, embedding);
    }

    private Map<String, Object> readMetadata(String metadataJson) {
        if (metadataJson == null || metadataJson.isBlank()) {
            return Map.of();
        }
        try {
            return objectMapper.readValue(metadataJson, MAP_TYPE);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read metadata from a legacy vector chunk", exception);
        }
    }

    static float[] parseVector(String vectorText) {
        if (vectorText == null || vectorText.length() < 2
                || vectorText.charAt(0) != '[' || vectorText.charAt(vectorText.length() - 1) != ']') {
            throw new IllegalArgumentException("Invalid legacy vector value");
        }
        String values = vectorText.substring(1, vectorText.length() - 1);
        if (values.isBlank()) {
            return new float[0];
        }
        String[] components = values.split(",");
        float[] vector = new float[components.length];
        for (int index = 0; index < components.length; index++) {
            vector[index] = Float.parseFloat(components[index].trim());
        }
        return vector;
    }
}
