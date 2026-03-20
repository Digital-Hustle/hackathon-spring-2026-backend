package com.hustle.rag_workspace_ms.controller.impl;

import com.hustle.rag_workspace_ms.controller.DocumentController;
import com.hustle.rag_workspace_ms.dto.response.DocumentMetaRs;
import com.hustle.rag_workspace_ms.mapper.DocumentMetaMapper;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.service.domain.impl.DocumentProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {

    private final DocumentMetaMapper documentMetaMapper;
    private final DocumentProcessing documentProcessing;

    @Override
    public DocumentMetaRs uploadDocument(UUID workspaceId, MultipartFile file) {

        DocumentMeta documentMeta = documentProcessing.processUpload(workspaceId, file);

        return documentMetaMapper.convert(documentMeta);
    }
}
