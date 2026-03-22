package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines;

public interface YandexGptService {
    String generateResponse(String prompt, Double temperature, Integer maxTokens);
}