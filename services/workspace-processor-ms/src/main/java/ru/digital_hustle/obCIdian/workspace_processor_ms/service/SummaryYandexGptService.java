package ru.digital_hustle.obCIdian.workspace_processor_ms.service;

import reactor.core.publisher.Mono;
import ru.digital_hustle.obCIdian.workspace_processor_ms.model.SummaryEntity;

import java.util.List;
import java.util.UUID;

public interface SummaryYandexGptService {
    Mono<String> generateResponseAsync(String prompt, Double temperature, Integer maxTokens);

    Mono<String> generateAndSaveSummaryAsync(UUID workspaceId, List<UUID> documentIds, String prompt, Double temperature, Integer maxTokens);

    List<SummaryEntity> getSummariesByWorkspaceId(UUID workspaceId);
}
