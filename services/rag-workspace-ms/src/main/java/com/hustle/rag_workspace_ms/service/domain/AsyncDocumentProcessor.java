package com.hustle.rag_workspace_ms.service.domain;

import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;

import java.util.UUID;

public interface AsyncDocumentProcessor {

    void processDocument(UUID workspaceId, DocumentMeta documentMeta);
}
