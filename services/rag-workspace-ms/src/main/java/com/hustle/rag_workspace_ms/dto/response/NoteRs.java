package com.hustle.rag_workspace_ms.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record NoteRs(
        UUID id,
        UUID workspaceId,
        String title,
        JsonNode contentJson
) {
}