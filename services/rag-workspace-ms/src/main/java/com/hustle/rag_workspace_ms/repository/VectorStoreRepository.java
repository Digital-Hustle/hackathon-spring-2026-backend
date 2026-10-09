package com.hustle.rag_workspace_ms.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hustle.rag_workspace_ms.model.SearchResult;
import com.hustle.rag_workspace_ms.model.VectorStoreChunk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Repository
public class VectorStoreRepository {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() { };

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final EmbeddingModel embeddingModel;
    private final String qdrantUrl;
    private final String collection;
    private final int vectorSize;

    private volatile boolean collectionReady;

    public VectorStoreRepository(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            EmbeddingModel embeddingModel,
            @Value("${app.vector-store.qdrant.url:http://localhost:6333}") String qdrantUrl,
            @Value("${app.vector-store.qdrant.collection:rag-chunks}") String collection,
            @Value("${app.vector-store.qdrant.vector-size:1024}") int vectorSize
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.embeddingModel = embeddingModel;
        this.qdrantUrl = qdrantUrl.replaceAll("/+$", "");
        this.collection = collection;
        this.vectorSize = vectorSize;
    }

    public void saveChunk(UUID id, UUID workspaceId, String content, Map<String, Object> metadata, float[] vector) {
        validateVector(vector);
        ensureCollection();

        ObjectNode point = objectMapper.createObjectNode();
        point.put("id", id.toString());
        point.set("vector", toJsonVector(vector));

        ObjectNode payload = point.putObject("payload");
        payload.put("workspaceId", workspaceId.toString());
        payload.put("content", content);
        payload.set("metadata", objectMapper.valueToTree(metadata == null ? Map.of() : metadata));

        ObjectNode request = objectMapper.createObjectNode();
        request.putArray("points").add(point);
        restTemplate.exchange(
                collectionUri("/points").queryParam("wait", true).toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(request),
                JsonNode.class
        );
    }

    public List<VectorStoreChunk> findSimilar(String queryText, UUID workspaceId, int topK) {
        float[] vector = embeddingModel.embed(queryText);
        return search(workspaceId, null, vector, topK).stream()
                .map(result -> new VectorStoreChunk(
                        result.id(), result.content(), result.metadata(), 1.0 - result.score()
                ))
                .toList();
    }

    public List<SearchResult> searchSimilar(UUID workspaceId, float[] queryVector, int topK) {
        return search(workspaceId, null, queryVector, topK);
    }

    public List<SearchResult> searchSimilarByDocument(
            UUID workspaceId, UUID documentId, float[] queryVector, int topK
    ) {
        return search(workspaceId, documentId, queryVector, topK);
    }

    public void deleteByDocumentId(UUID workspaceId, UUID documentId) {
        ensureCollection();

        ObjectNode request = objectMapper.createObjectNode();
        ArrayNode must = request.putObject("filter").putArray("must");
        must.add(payloadMatch("workspaceId", workspaceId.toString()));
        must.add(payloadMatch("metadata.documentId", documentId.toString()));

        restTemplate.exchange(
                collectionUri("/points/delete").queryParam("wait", true).toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(request),
                JsonNode.class
        );
    }

    private List<SearchResult> search(UUID workspaceId, UUID documentId, float[] queryVector, int topK) {
        validateVector(queryVector);
        if (topK < 1) {
            throw new IllegalArgumentException("topK must be greater than zero");
        }
        ensureCollection();

        ObjectNode request = objectMapper.createObjectNode();
        request.set("query", toJsonVector(queryVector));
        request.put("limit", topK);
        request.put("with_payload", true);

        ObjectNode filter = request.putObject("filter");
        ArrayNode must = filter.putArray("must");
        must.add(payloadMatch("workspaceId", workspaceId.toString()));
        if (documentId != null) {
            must.add(payloadMatch("metadata.documentId", documentId.toString()));
        }

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                collectionUri("/points/query").toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(request),
                JsonNode.class
        );

        JsonNode points = response.getBody() == null
                ? objectMapper.createArrayNode()
                : response.getBody().path("result").path("points");
        List<SearchResult> results = new ArrayList<>();
        for (JsonNode point : points) {
            JsonNode payload = point.path("payload");
            JsonNode metadataNode = payload.path("metadata");
            Map<String, Object> metadata = metadataNode.isObject()
                    ? objectMapper.convertValue(metadataNode, MAP_TYPE)
                    : Map.of();
            double score = point.path("score").asDouble(0.0);
            score = Math.max(0.0, Math.min(1.0, score));
            results.add(new SearchResult(
                    UUID.fromString(point.path("id").asText()),
                    payload.path("content").asText(""),
                    metadata,
                    score,
                    results.size() + 1
            ));
        }
        return results;
    }

    private ObjectNode payloadMatch(String key, String value) {
        ObjectNode condition = objectMapper.createObjectNode();
        condition.put("key", key);
        condition.putObject("match").put("value", value);
        return condition;
    }

    private ArrayNode toJsonVector(float[] vector) {
        ArrayNode values = objectMapper.createArrayNode();
        for (float value : vector) {
            values.add(value);
        }
        return values;
    }

    private void validateVector(float[] vector) {
        if (vector == null || vector.length != vectorSize) {
            throw new IllegalArgumentException(
                    "Expected an embedding with " + vectorSize + " dimensions"
            );
        }
    }

    private void ensureCollection() {
        if (collectionReady) {
            return;
        }
        synchronized (this) {
            if (collectionReady) {
                return;
            }
            String uri = collectionUri("").toUriString();
            try {
                JsonNode collectionInfo = restTemplate.getForObject(uri, JsonNode.class);
                int existingVectorSize = collectionInfo == null
                        ? -1
                        : collectionInfo.path("result").path("config").path("params")
                                .path("vectors").path("size").asInt(-1);
                if (existingVectorSize != -1 && existingVectorSize != vectorSize) {
                    throw new IllegalStateException(
                            "Qdrant collection " + collection + " has " + existingVectorSize
                                    + " dimensions; configured embeddings have " + vectorSize
                    );
                }
            } catch (HttpClientErrorException.NotFound exception) {
                ObjectNode vectorConfig = objectMapper.createObjectNode();
                vectorConfig.put("size", vectorSize);
                vectorConfig.put("distance", "Cosine");
                ObjectNode request = objectMapper.createObjectNode();
                request.set("vectors", vectorConfig);
                try {
                    restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(request), JsonNode.class);
                } catch (HttpClientErrorException.Conflict conflict) {
                    log.debug("Qdrant collection {} was created concurrently", collection);
                }
            }
            collectionReady = true;
        }
    }

    private UriComponentsBuilder collectionUri(String suffix) {
        return UriComponentsBuilder.fromHttpUrl(qdrantUrl)
                .pathSegment("collections", collection)
                .path(suffix);
    }
}
