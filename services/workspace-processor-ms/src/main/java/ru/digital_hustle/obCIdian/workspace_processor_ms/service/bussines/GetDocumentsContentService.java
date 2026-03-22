package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines;

import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentsTextRs;

import java.util.UUID;

public interface GetDocumentsContentService {
    DocumentsTextRs getContent(UUID workspaceId);
}