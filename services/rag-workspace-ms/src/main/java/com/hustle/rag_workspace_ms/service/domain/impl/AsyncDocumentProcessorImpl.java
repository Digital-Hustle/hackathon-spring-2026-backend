package com.hustle.rag_workspace_ms.service.domain.impl;

import com.hustle.rag_workspace_ms.enums.FileProcessingStatus;
import com.hustle.rag_workspace_ms.factory.FileParserFactory;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.repository.VectorStoreRepository;
import com.hustle.rag_workspace_ms.service.domain.AsyncDocumentProcessor;
import com.hustle.rag_workspace_ms.service.entity.DocumentMetaService;
import com.hustle.rag_workspace_ms.utils.FileParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncDocumentProcessorImpl implements AsyncDocumentProcessor {

    private final FileParserFactory fileParserFactory;
    private final VectorStoreRepository vectorStoreRepository;
    private final EmbeddingModel embeddingModel;
    private final TextSplitter textSplitter;
    private final DocumentMetaService documentMetaService;

    @Async
    public void processDocument(UUID workspaceId, DocumentMeta documentMeta, MultipartFile document) {
        UUID documentId = documentMeta.getId();

        try (InputStream is = document.getInputStream()) {
            // Парсинг
            FileParser parser = fileParserFactory.getParser(documentMeta.getContentType());
            String extractedText = parser.extractText(is, documentMeta.getOriginalName());

            Document sourceDoc = new Document(
                    extractedText,
                    Map.of(
                            "documentId", documentId.toString(),
                            "fileName", documentMeta.getOriginalName(),
                            "workspaceId", workspaceId.toString()
                    )
            );

            List<Document> chunks = textSplitter.split(sourceDoc);

            for (Document chunk : chunks) {
                String chunkText = chunk.getText();
                float[] vector = embeddingModel.embed(chunkText);

                Map<String, Object> metadata = new HashMap<>();
                metadata.put("documentId", documentId.toString());
                metadata.put("fileName", documentMeta.getOriginalName());
                metadata.put("workspaceId", workspaceId.toString());

                vectorStoreRepository.saveChunk(
                        UUID.randomUUID(),
                        workspaceId,
                        chunkText,
                        metadata,
                        vector
                );
            }

            documentMetaService.update(
                    documentMeta.getId(),
                    documentMeta.toBuilder()
                            .status(FileProcessingStatus.PROCESSED)
                            .build()
            );
            log.info("Document {} processed successfully", documentId);

        } catch (Exception exception) {
            log.error("Processing failed for document {}", documentId, exception);
            documentMetaService.update(
                    documentMeta.getId(),
                    documentMeta.toBuilder()
                            .status(FileProcessingStatus.FAILED)
                            .errorMessage(exception.getMessage())
                            .build()
            );
        }
    }
}