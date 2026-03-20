package com.hustle.rag_workspace_ms.service.domain;

import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface DocumentServiceProc {

    DocumentMeta processUpload(UUID workspaceId, MultipartFile file);
}
