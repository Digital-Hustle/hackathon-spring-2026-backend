package com.hustle.rag_workspace_ms.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record NotePreviewRs(
        UUID id,
        String title
) {
}