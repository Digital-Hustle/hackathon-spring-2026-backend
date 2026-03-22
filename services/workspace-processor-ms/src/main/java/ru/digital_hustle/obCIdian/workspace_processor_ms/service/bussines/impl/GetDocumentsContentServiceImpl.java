package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentText;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentsTextRs;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.GetDocumentsContentService;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.client.ContentFeignClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetDocumentsContentServiceImpl implements GetDocumentsContentService {

    private final ContentFeignClient contentFeignClient;

    @Value("${app.rag.enabled:true}")
    private boolean ragEnabled;

    @Value("${app.audio.stub-text:Это тестовый текст для генерации аудио-диалога.}")
    private String stubText;

    @Override
    public DocumentsTextRs getContent(UUID workspaceId) {
        if (!ragEnabled) {
            return new DocumentsTextRs(
                    List.of(new DocumentText(UUID.randomUUID(), stubText))
            );
        }

        return contentFeignClient.getDocumentsContent(workspaceId);
    }
}