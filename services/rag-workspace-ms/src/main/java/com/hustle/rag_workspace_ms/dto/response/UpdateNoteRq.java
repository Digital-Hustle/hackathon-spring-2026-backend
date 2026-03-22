package com.hustle.rag_workspace_ms.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;

@Builder(toBuilder = true)
public record UpdateNoteRq(
        String title,
        JsonNode contentJson
) {
}