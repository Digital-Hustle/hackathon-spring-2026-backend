package com.hustle.rag_workspace_ms.dto.response;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record DocumentsTextRs(

        List<DocumentTextDto> documents
) {
}
