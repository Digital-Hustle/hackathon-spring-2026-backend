package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record DocumentsTextRs(
        List<DocumentText> documents
) {
}
