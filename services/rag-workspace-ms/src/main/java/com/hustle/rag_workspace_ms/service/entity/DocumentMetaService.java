package com.hustle.rag_workspace_ms.service.entity;


import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;

import java.util.UUID;

public interface DocumentMetaService {

    DocumentMeta getByOwnerId(UUID workspaceId);

    DocumentMeta getById(UUID id);

    DocumentMeta create(DocumentMeta photoMetaInfo);

    DocumentMeta update(UUID id, DocumentMeta photoMetaInfo);

    void delete(UUID id);

    boolean existsByWorkspaceId(UUID workspaceId);
}
