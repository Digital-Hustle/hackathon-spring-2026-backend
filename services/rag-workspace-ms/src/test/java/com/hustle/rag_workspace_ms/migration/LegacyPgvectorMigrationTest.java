package com.hustle.rag_workspace_ms.migration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hustle.rag_workspace_ms.repository.VectorStoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationArguments;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class LegacyPgvectorMigrationTest {

    @Test
    void parsesPgvectorTextIntoFloatComponents() {
        assertThat(LegacyPgvectorMigration.parseVector("[0.25, -1.5, 3.0e-2]"))
                .containsExactly(0.25f, -1.5f, 0.03f);
    }

    @Test
    void skipsDatabasesWithoutLegacyVectorTable() throws Exception {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        VectorStoreRepository vectorStoreRepository = mock(VectorStoreRepository.class);
        when(jdbcTemplate.queryForObject(anyString(), eq(Boolean.class))).thenReturn(false);

        new LegacyPgvectorMigration(jdbcTemplate, new ObjectMapper(), vectorStoreRepository)
                .run(mock(ApplicationArguments.class));

        verifyNoInteractions(vectorStoreRepository);
    }

    @Test
    void dropsLegacyStorageOnlyAfterChunkIsCopiedToQdrant() throws Exception {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        VectorStoreRepository vectorStoreRepository = mock(VectorStoreRepository.class);
        UUID chunkId = UUID.randomUUID();
        UUID workspaceId = UUID.randomUUID();
        when(jdbcTemplate.queryForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        doAnswer(invocation -> {
            RowCallbackHandler handler = invocation.getArgument(1);
            ResultSet row = mock(ResultSet.class);
            when(row.getObject("id", UUID.class)).thenReturn(chunkId);
            when(row.getObject("workspace_id", UUID.class)).thenReturn(workspaceId);
            when(row.getString("content")).thenReturn("legacy chunk");
            when(row.getString("metadata")).thenReturn("{\"documentId\":\"doc-1\"}");
            when(row.getString("embedding")).thenReturn("[0.1,0.2,0.3]");
            handler.processRow(row);
            return null;
        }).when(jdbcTemplate).query(anyString(), any(RowCallbackHandler.class));

        new LegacyPgvectorMigration(jdbcTemplate, new ObjectMapper(), vectorStoreRepository)
                .run(mock(ApplicationArguments.class));

        var order = inOrder(vectorStoreRepository, jdbcTemplate);
        order.verify(vectorStoreRepository).saveChunk(
                eq(chunkId), eq(workspaceId), eq("legacy chunk"), any(),
                argThat(vector -> Arrays.equals(vector, new float[]{0.1f, 0.2f, 0.3f}))
        );
        order.verify(jdbcTemplate).execute("DROP TABLE IF EXISTS rag.vector_store");
        order.verify(jdbcTemplate).execute("DROP EXTENSION IF EXISTS vector");
    }

    @Test
    void keepsLegacyDataWhenQdrantCopyFails() throws Exception {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        VectorStoreRepository vectorStoreRepository = mock(VectorStoreRepository.class);
        when(jdbcTemplate.queryForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        doAnswer(invocation -> {
            RowCallbackHandler handler = invocation.getArgument(1);
            ResultSet row = mock(ResultSet.class);
            when(row.getObject("id", UUID.class)).thenReturn(UUID.randomUUID());
            when(row.getObject("workspace_id", UUID.class)).thenReturn(UUID.randomUUID());
            when(row.getString("content")).thenReturn("legacy chunk");
            when(row.getString("metadata")).thenReturn("{}");
            when(row.getString("embedding")).thenReturn("[0.1,0.2,0.3]");
            handler.processRow(row);
            return null;
        }).when(jdbcTemplate).query(anyString(), any(RowCallbackHandler.class));
        doThrow(new IllegalStateException("Qdrant unavailable"))
                .when(vectorStoreRepository).saveChunk(any(), any(), anyString(), any(), any());

        LegacyPgvectorMigration migration = new LegacyPgvectorMigration(
                jdbcTemplate, new ObjectMapper(), vectorStoreRepository
        );
        assertThatThrownBy(() -> migration.run(mock(ApplicationArguments.class)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Qdrant unavailable");
        verify(jdbcTemplate, org.mockito.Mockito.never()).execute(anyString());
    }
}
