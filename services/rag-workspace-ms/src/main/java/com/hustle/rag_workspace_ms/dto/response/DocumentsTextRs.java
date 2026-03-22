package com.hustle.rag_workspace_ms.dto.response;

import com.hustle.rag_workspace_ms.dto.DocumentTextDto;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record DocumentsTextRs(

        List<DocumentTextDto> documents
) {
}
