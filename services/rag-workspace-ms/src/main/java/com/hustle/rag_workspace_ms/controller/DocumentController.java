package com.hustle.rag_workspace_ms.controller;

import com.hustle.rag_workspace_ms.dto.DocumentMetaDto;
import com.hustle.rag_workspace_ms.dto.request.UpdateDocumentActivityRq;
import com.hustle.rag_workspace_ms.dto.response.DocumentsTextRs;
import com.hustle.rag_workspace_ms.dto.response.GetDocumentsMetaRs;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequestMapping("/api/v1/workspaces/{workspaceId}/documents")
public interface DocumentController {

    @GetMapping
    GetDocumentsMetaRs getByWorkspaceId(@PathVariable UUID workspaceId);

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    DocumentMetaDto uploadDocument(@PathVariable UUID workspaceId, @RequestParam("file") MultipartFile file);

    @PatchMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateDocumentActivity(
            @PathVariable UUID documentId,
            @RequestBody UpdateDocumentActivityRq updateDocumentActivityRq
    );

    @GetMapping("/content")
    DocumentsTextRs receiveIds(@PathVariable UUID workspaceId);
}
