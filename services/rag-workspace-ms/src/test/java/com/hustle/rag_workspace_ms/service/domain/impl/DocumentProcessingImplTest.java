package com.hustle.rag_workspace_ms.service.domain.impl;

import com.hustle.rag_workspace_ms.enums.FileExtension;
import com.hustle.rag_workspace_ms.enums.FileProcessingStatus;
import com.hustle.rag_workspace_ms.factory.FileParserFactory;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.repository.VectorStoreRepository;
import com.hustle.rag_workspace_ms.service.domain.AsyncDocumentProcessor;
import com.hustle.rag_workspace_ms.service.entity.DocumentMetaService;
import com.hustle.rag_workspace_ms.service.entity.DocumentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DocumentProcessingImplTest {

    private final DocumentMetaService documentMetaService = mock(DocumentMetaService.class);
    private final DocumentService documentService = mock(DocumentService.class);
    private final AsyncDocumentProcessor asyncDocumentProcessor = mock(AsyncDocumentProcessor.class);
    private final FileParserFactory fileParserFactory = mock(FileParserFactory.class);
    private final VectorStoreRepository vectorStoreRepository = mock(VectorStoreRepository.class);
    private final DocumentProcessingImpl documentProcessing = new DocumentProcessingImpl(
            documentMetaService,
            documentService,
            asyncDocumentProcessor,
            fileParserFactory,
            vectorStoreRepository
    );

    @Test
    void deletesDocumentVectorsObjectAndMetadataInThatOrder() {
        UUID workspaceId = UUID.randomUUID();
        UUID documentId = UUID.randomUUID();
        DocumentMeta documentMeta = documentMeta(workspaceId, documentId, FileProcessingStatus.PROCESSED);
        when(documentMetaService.getById(documentId)).thenReturn(documentMeta);

        documentProcessing.deleteDocument(workspaceId, documentId);

        var order = inOrder(vectorStoreRepository, documentService, documentMetaService);
        order.verify(vectorStoreRepository).deleteByDocumentId(workspaceId, documentId);
        order.verify(documentService).delete(documentId + ".pdf");
        order.verify(documentMetaService).delete(documentId);
    }

    @Test
    void doesNotDeleteDocumentWhileProcessingIsInProgress() {
        UUID workspaceId = UUID.randomUUID();
        UUID documentId = UUID.randomUUID();
        when(documentMetaService.getById(documentId))
                .thenReturn(documentMeta(workspaceId, documentId, FileProcessingStatus.PENDING));

        assertThatThrownBy(() -> documentProcessing.deleteDocument(workspaceId, documentId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(exception -> ((ResponseStatusException) exception).getStatusCode())
                .isEqualTo(HttpStatus.CONFLICT);

        verify(vectorStoreRepository, never()).deleteByDocumentId(workspaceId, documentId);
        verify(documentService, never()).delete(documentId + ".pdf");
        verify(documentMetaService, never()).delete(documentId);
    }

    @Test
    void doesNotDeleteDocumentsFromAnotherWorkspace() {
        UUID workspaceId = UUID.randomUUID();
        UUID documentId = UUID.randomUUID();
        when(documentMetaService.getById(documentId))
                .thenReturn(documentMeta(UUID.randomUUID(), documentId, FileProcessingStatus.PROCESSED));

        assertThatThrownBy(() -> documentProcessing.deleteDocument(workspaceId, documentId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(exception -> ((ResponseStatusException) exception).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(vectorStoreRepository, never()).deleteByDocumentId(workspaceId, documentId);
        verify(documentService, never()).delete(documentId + ".pdf");
        verify(documentMetaService, never()).delete(documentId);
    }

    @Test
    void returnsNotFoundWhenDocumentDoesNotExist() {
        UUID workspaceId = UUID.randomUUID();
        UUID documentId = UUID.randomUUID();
        when(documentMetaService.getById(documentId)).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> documentProcessing.deleteDocument(workspaceId, documentId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(exception -> ((ResponseStatusException) exception).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    private DocumentMeta documentMeta(UUID workspaceId, UUID documentId, FileProcessingStatus status) {
        return DocumentMeta.builder()
                .id(documentId)
                .workspaceId(workspaceId)
                .extension(FileExtension.PDF)
                .status(status)
                .build();
    }
}
