package ru.digital_hustle.obCIdian.workspace_processor_ms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request.SummaryYandexGptRq;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.SummaryYandexGptRs;
import ru.digital_hustle.obCIdian.workspace_processor_ms.model.SummaryEntity;
import ru.digital_hustle.obCIdian.workspace_processor_ms.repository.SummaryRepository;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.SummaryYandexGptService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SummaryYandexGptServiceImpl implements SummaryYandexGptService {

    private static final String YANDEX_CLOUD_FOLDER = "b1g43athfi6adknk4fk5";
    private static final String YANDEX_CLOUD_MODEL = "aliceai-llm";

    private final WebClient yandexGptWebClient;

    private final SummaryRepository summaryRepository;

    @Override
    public Mono<String> generateResponseAsync(String prompt, Double temperature, Integer maxTokens) {
        String model = "gpt://" + YANDEX_CLOUD_FOLDER + "/" + YANDEX_CLOUD_MODEL;

        SummaryYandexGptRq request = new SummaryYandexGptRq(
                model,
                prompt,
                temperature,
                maxTokens
        );

        return yandexGptWebClient.post()
                .uri("/responses")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> Mono.error(new RuntimeException("Yandex GPT API error: " + clientResponse.statusCode()))
                )
                .bodyToMono(SummaryYandexGptRs.class)
                .map(response -> {
                    if (response.getOutput() != null &&
                            !response.getOutput().isEmpty() &&
                            response.getOutput().get(0).getContent() != null &&
                            !response.getOutput().get(0).getContent().isEmpty()) {

                        return response.getOutput().get(0).getContent().get(0).getText();
                    }
                    return "No response from AI";
                })
                .onErrorResume(throwable -> Mono.just("Error calling Yandex GPT: " + throwable.getMessage()));
    }

    public Mono<String> generateAndSaveSummaryAsync(UUID workspaceId,
                                                    List<UUID> documentIds,
                                                    String prompt,
                                                    Double temperature,
                                                    Integer maxTokens) {

        //summaryRepository.deleteAllByWorkspaceId(workspaceId);

        return generateResponseAsync(prompt, temperature, maxTokens)
                .flatMap(summary -> {
                    var entity = SummaryEntity.builder()
                            .workspaceId(workspaceId)
                            .documentIds(documentIds)
                            .content(summary)
                            .build();

                    return Mono.fromCallable(() -> summaryRepository.save(entity))
                            .subscribeOn(Schedulers.boundedElastic())
                            .thenReturn(summary);
                });
    }

    @Transactional(readOnly = true)
    public List<SummaryEntity> getSummariesByWorkspaceId(UUID workspaceId) {
        return summaryRepository.findAllByWorkspaceIdOrderByCreatedAtDesc(workspaceId);
    }

}
