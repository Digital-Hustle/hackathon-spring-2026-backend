package ru.digital_hustle.obCIdian.workspace_processor_ms.controller;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request.WorkspaceIdRq;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.SummaryItemRs;

import java.util.List;
import java.util.UUID;

public interface SummaryController {
    Mono<ResponseEntity<String>> generateSummary(WorkspaceIdRq request);

    ResponseEntity<List<SummaryItemRs>> getSummariesByWorkspace(UUID workspaceId);
}
