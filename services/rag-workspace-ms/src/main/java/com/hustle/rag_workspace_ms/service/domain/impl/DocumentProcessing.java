package com.hustle.rag_workspace_ms.service.domain.impl;

import com.hustle.rag_workspace_ms.factory.DocumentMetaFactory;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.service.entity.DocumentMetaService;
import com.hustle.rag_workspace_ms.service.entity.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentProcessing {

    private final DocumentMetaService documentMetaService;
    private final DocumentService documentService;
    private final AsyncDocumentProcessor asyncDocumentProcessor;

    public DocumentMeta processUpload(UUID workspaceId, MultipartFile document) {
        DocumentMeta documentMeta = DocumentMetaFactory.newProcessingPhotoMetaInfo(workspaceId, document);
        DocumentMeta createdDocumentMeta = documentMetaService.create(documentMeta);

        asyncDocumentProcessor.processDocument(workspaceId, createdDocumentMeta, document);

        String minioKey = "%s.%s".formatted(
                createdDocumentMeta.getId(), FilenameUtils.getExtension(document.getOriginalFilename())
        ); // TODO вообще это нужно в хелпер

        documentService.save(minioKey, document);

        return createdDocumentMeta;
    }
}