package com.hustle.rag_workspace_ms.service.entity;


import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;

import java.util.List;
import java.util.UUID;

public interface DocumentMetaService {

    List<DocumentMeta> getAllByOwnerId(UUID workspaceId);

    DocumentMeta getById(UUID id);

    DocumentMeta create(DocumentMeta photoMetaInfo);

    DocumentMeta update(UUID id, DocumentMeta photoMetaInfo);

    void updateActivity(UUID id, Boolean isActive);

    void delete(UUID id);

    boolean existsByWorkspaceId(UUID workspaceId);
}
