package com.hustle.rag_workspace_ms.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record CreateNoteRq(
        String title,
        JsonNode contentJson
) {
}