package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request.YandexGptCompletionRequest;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.YandexGptCompletionResponse;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.YandexGptService;


import java.util.List;

@Service
@RequiredArgsConstructor
public class YandexGptServiceImpl implements YandexGptService {

    private static final String YANDEX_CLOUD_FOLDER = "b1g43athfi6adknk4fk5";
    private static final String YANDEX_CLOUD_MODEL = "yandexgpt-lite";

    @Value("${yandex.gpt.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public String generateResponse(String prompt, Double temperature, Integer maxTokens) {
        String modelUri = "gpt://" + YANDEX_CLOUD_FOLDER + "/" + YANDEX_CLOUD_MODEL;
        double temp = temperature != null ? temperature : 0.7;
        int tokens = maxTokens != null ? maxTokens : 5000;

        YandexGptCompletionRequest request = new YandexGptCompletionRequest(
                modelUri,
                new YandexGptCompletionRequest.CompletionOptions(temp, tokens),
                List.of(new YandexGptCompletionRequest.Message("user", prompt))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Api-Key " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<YandexGptCompletionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<YandexGptCompletionResponse> response = restTemplate.exchange(
                "https://llm.api.cloud.yandex.net/foundationModels/v1/completion",
                HttpMethod.POST,
                entity,
                YandexGptCompletionResponse.class
        );

        if (response.getBody() != null && response.getBody().getResult() != null &&
                response.getBody().getResult().getAlternatives() != null &&
                !response.getBody().getResult().getAlternatives().isEmpty()) {
            return response.getBody().getResult().getAlternatives().get(0).getMessage().getText();
        }
        return "No response from AI";
    }
}