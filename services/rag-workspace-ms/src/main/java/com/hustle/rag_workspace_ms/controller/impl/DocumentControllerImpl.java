package com.hustle.rag_workspace_ms.controller.impl;

import com.hustle.rag_workspace_ms.controller.DocumentController;
import com.hustle.rag_workspace_ms.dto.DocumentMetaDto;
import com.hustle.rag_workspace_ms.dto.request.UpdateDocumentActivityRq;
import com.hustle.rag_workspace_ms.dto.response.DocumentsTextRs;
import com.hustle.rag_workspace_ms.dto.response.GetDocumentsMetaRs;
import com.hustle.rag_workspace_ms.mapper.DocumentMetaMapper;
import com.hustle.rag_workspace_ms.model.DocumentText;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.service.domain.DocumentProcessing;
import com.hustle.rag_workspace_ms.service.entity.DocumentMetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {

    private final DocumentMetaMapper documentMetaMapper;
    private final DocumentProcessing documentProcessing;
    private final DocumentMetaService documentMetaService;

    @Override
    public GetDocumentsMetaRs getByWorkspaceId(UUID workspaceId) {
        List<DocumentMeta> documents = documentMetaService.getAllByOwnerId(workspaceId);

        return GetDocumentsMetaRs.builder()
                .documents(documentMetaMapper.convertDocumentMeta(documents))
                .build();
    }

    @Override
    public DocumentMetaDto uploadDocument(UUID workspaceId, MultipartFile file) {
        DocumentMeta documentMeta = documentProcessing.processUpload(workspaceId, file);

        return documentMetaMapper.convert(documentMeta);
    }

    @Override
    public void updateDocumentActivity(UUID documentId, UpdateDocumentActivityRq updateDocumentActivityRq) {
        Boolean isActive = updateDocumentActivityRq.isActive();

        documentMetaService.updateActivity(documentId, isActive);
    }

    @Override
    public DocumentsTextRs receiveIds(UUID workspaceId) {
        // TODO тут нужно ещё поработать с флагом, чтоб отдавать только по активным файлам инфу
        List<DocumentText> documentsContent = documentProcessing.getDocumentsContent(workspaceId);

        return DocumentsTextRs.builder()
                .documents(documentMetaMapper.convertDocumentText(documentsContent))
                .build();
    }
}
