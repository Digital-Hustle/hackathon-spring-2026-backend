package com.hustle.rag_workspace_ms.controller;

import com.hustle.rag_workspace_ms.dto.response.DocumentMetaRs;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequestMapping("/api/v1/workspaces/{workspaceId}/documents")
public interface DocumentController {

    @PostMapping
    DocumentMetaRs uploadDocument(@PathVariable UUID workspaceId, @RequestParam("file") MultipartFile file);
}
