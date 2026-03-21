package com.hustle.rag_workspace_ms.dto.response;

import com.hustle.rag_workspace_ms.dto.DocumentMetaDto;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record GetDocumentsMetaRs(

        List<DocumentMetaDto> documents
) {
}
