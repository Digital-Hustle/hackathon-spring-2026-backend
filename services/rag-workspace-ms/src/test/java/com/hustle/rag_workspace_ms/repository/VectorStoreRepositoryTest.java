package com.hustle.rag_workspace_ms.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hustle.rag_workspace_ms.model.SearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class VectorStoreRepositoryTest {

    private static final String COLLECTION_URL = "http://localhost:6333/collections/rag-chunks";

    @Test
    void createsCollectionUpsertsChunkAndSearchesWithinWorkspaceAndDocument() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        VectorStoreRepository repository = new VectorStoreRepository(
                restTemplate,
                new ObjectMapper(),
                org.mockito.Mockito.mock(EmbeddingModel.class),
                "http://localhost:6333",
                "rag-chunks",
                3
        );

        UUID chunkId = UUID.randomUUID();
        UUID workspaceId = UUID.randomUUID();
        UUID documentId = UUID.randomUUID();

        server.expect(requestTo(COLLECTION_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(NOT_FOUND));
        server.expect(requestTo(COLLECTION_URL))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(jsonPath("$.vectors.size").value(3))
                .andExpect(jsonPath("$.vectors.distance").value("Cosine"))
                .andRespond(withSuccess("{}", org.springframework.http.MediaType.APPLICATION_JSON));
        server.expect(requestTo(org.hamcrest.Matchers.containsString(COLLECTION_URL + "/points?wait=true")))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(jsonPath("$.points[0].id").value(chunkId.toString()))
                .andExpect(jsonPath("$.points[0].payload.workspaceId").value(workspaceId.toString()))
                .andExpect(jsonPath("$.points[0].payload.metadata.documentId").value(documentId.toString()))
                .andRespond(withSuccess("{}", org.springframework.http.MediaType.APPLICATION_JSON));
        server.expect(requestTo(COLLECTION_URL + "/points/query"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.filter.must[0].key").value("workspaceId"))
                .andExpect(jsonPath("$.filter.must[0].match.value").value(workspaceId.toString()))
                .andExpect(jsonPath("$.filter.must[1].key").value("metadata.documentId"))
                .andExpect(jsonPath("$.filter.must[1].match.value").value(documentId.toString()))
                .andRespond(withSuccess("""
                        {"result":{"points":[{"id":"%s","score":0.91,"payload":{
                          "content":"matched chunk","metadata":{"documentId":"%s","fileName":"notes.txt"}
                        }}]}}
                        """.formatted(chunkId, documentId), org.springframework.http.MediaType.APPLICATION_JSON));

        repository.saveChunk(chunkId, workspaceId, "matched chunk", Map.of("documentId", documentId.toString()),
                new float[]{0.1f, 0.2f, 0.3f});
        List<SearchResult> results = repository.searchSimilarByDocument(
                workspaceId, documentId, new float[]{0.3f, 0.2f, 0.1f}, 5
        );

        assertThat(results).containsExactly(new SearchResult(
                chunkId,
                "matched chunk",
                Map.of("documentId", documentId.toString(), "fileName", "notes.txt"),
                0.91,
                1
        ));
        server.verify();
    }

    @Test
    void deletesOnlyPointsForTheRequestedWorkspaceAndDocument() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        VectorStoreRepository repository = new VectorStoreRepository(
                restTemplate,
                new ObjectMapper(),
                org.mockito.Mockito.mock(EmbeddingModel.class),
                "http://localhost:6333",
                "rag-chunks",
                3
        );
        UUID workspaceId = UUID.randomUUID();
        UUID documentId = UUID.randomUUID();

        server.expect(requestTo(COLLECTION_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {"result":{"config":{"params":{"vectors":{"size":3}}}}}
                        """, org.springframework.http.MediaType.APPLICATION_JSON));
        server.expect(requestTo(COLLECTION_URL + "/points/delete?wait=true"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.filter.must[0].key").value("workspaceId"))
                .andExpect(jsonPath("$.filter.must[0].match.value").value(workspaceId.toString()))
                .andExpect(jsonPath("$.filter.must[1].key").value("metadata.documentId"))
                .andExpect(jsonPath("$.filter.must[1].match.value").value(documentId.toString()))
                .andRespond(withSuccess("{}", org.springframework.http.MediaType.APPLICATION_JSON));

        repository.deleteByDocumentId(workspaceId, documentId);

        server.verify();
    }
}
