package com.hustle.rag_workspace_ms.service.domain;

import com.hustle.rag_workspace_ms.model.DocumentText;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentProcessing {

    DocumentMeta processUpload(UUID workspaceId, MultipartFile document);

    List<DocumentText> getDocumentsContent(UUID workspaceId);
}
