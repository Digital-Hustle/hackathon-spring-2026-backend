package com.hustle.rag_workspace_ms.service.domain.impl;

import com.hustle.rag_workspace_ms.factory.DocumentMetaFactory;
import com.hustle.rag_workspace_ms.factory.FileParserFactory;
import com.hustle.rag_workspace_ms.enums.FileProcessingStatus;
import com.hustle.rag_workspace_ms.model.DocumentText;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.repository.VectorStoreRepository;
import com.hustle.rag_workspace_ms.service.domain.AsyncDocumentProcessor;
import com.hustle.rag_workspace_ms.service.domain.DocumentProcessing;
import com.hustle.rag_workspace_ms.service.entity.DocumentMetaService;
import com.hustle.rag_workspace_ms.service.entity.DocumentService;
import com.hustle.rag_workspace_ms.utils.DocumentObjectKey;
import com.hustle.rag_workspace_ms.utils.FileParser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO
//  написать ручку на получение всех workspace +
//  ручку Кире для аудио +
//  ручка на обновление состояния активности файла
//  чат прямо в этом микросе
//  подумать ещё с Киреной ручкой для генерации подкастов
//  точно ли я должен возвращать именно весь текст всех файлов

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentProcessingImpl implements DocumentProcessing {

    private final DocumentMetaService documentMetaService;
    private final DocumentService documentService;
    private final AsyncDocumentProcessor asyncDocumentProcessor;
    private final FileParserFactory fileParserFactory;
    private final VectorStoreRepository vectorStoreRepository;

    @Transactional
    @Override
    public DocumentMeta processUpload(UUID workspaceId, MultipartFile document) {
        DocumentMeta documentMeta = DocumentMetaFactory.newProcessingPhotoMetaInfo(workspaceId, document);
        DocumentMeta createdDocumentMeta = documentMetaService.create(documentMeta);

        documentService.save(DocumentObjectKey.from(createdDocumentMeta), document);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                asyncDocumentProcessor.processDocument(workspaceId, createdDocumentMeta);
            }
        });

        return createdDocumentMeta;
    }

    @Override
    public void deleteDocument(UUID workspaceId, UUID documentId) {
        DocumentMeta documentMeta;
        try {
            documentMeta = documentMetaService.getById(documentId);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found", exception);
        }

        if (!workspaceId.equals(documentMeta.getWorkspaceId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }
        if (documentMeta.getStatus() == FileProcessingStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Document processing is still in progress");
        }

        vectorStoreRepository.deleteByDocumentId(workspaceId, documentId);
        documentService.delete(DocumentObjectKey.from(documentMeta));
        documentMetaService.delete(documentId);
    }

    @Override
    public List<DocumentText> getDocumentsContent(UUID workspaceId) {
        List<DocumentMeta> documentsMeta = documentMetaService.getAllByOwnerId(workspaceId).stream()
                .filter(documentMeta -> Boolean.TRUE.equals(documentMeta.getIsActive()))
                .toList();
        Map<String, DocumentMeta> keyToDocumentMap = documentsMeta.stream()
                .collect(Collectors.toMap(
                        DocumentObjectKey::from,
                        Function.identity()
                ));

        return keyToDocumentMap.entrySet().stream()
                .map(entry -> {
                    String minioKey = entry.getKey();
                    DocumentMeta documentMeta = entry.getValue();

                    return getDocumentText(minioKey, documentMeta);
                })
                .toList();
    }

    private DocumentText getDocumentText(String minioKey, DocumentMeta documentMeta) {
        try (InputStream inputStream = documentService.downloadDocument(minioKey)) {
            FileParser parser = fileParserFactory.getParser(documentMeta.getContentType());
            String extractedText = parser.extractText(inputStream, documentMeta.getOriginalName());

            return DocumentText.builder()
                    .id(documentMeta.getId())
                    .content(extractedText)
                    .build();
        } catch (Exception exception) {
            throw new RuntimeException("Parsing failed for file: " + documentMeta.getOriginalName(), exception);
        }
    }
}
