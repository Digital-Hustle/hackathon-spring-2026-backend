package com.hustle.rag_workspace_ms.service.domain.impl;

import com.hustle.rag_workspace_ms.gateway.AiGateway;
import com.hustle.rag_workspace_ms.model.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlmService {

    private final AiGateway aiGateway;

    public String generateAnswer(String question, List<SearchResult> context) {
        log.info("Generating answer for question with {} context chunks", context.size());

        String contextText = context.stream()
                .map(SearchResult::content)
                .collect(Collectors.joining("\n\n---\n\n"));

        String prompt = buildPrompt(question, contextText);

        return generateWithLlm(prompt);
    }

    private String buildPrompt(String question, String context) {
        return """
                Ты — помощник, который отвечает на вопросы на основе предоставленного контекста.
                
                КОНТЕКСТ:
                %s
                
                ВОПРОС:
                %s
                
                ОТВЕТ (используй только информацию из контекста):
                """.formatted(context, question);
    }

    private String generateWithLlm(String prompt) {
        // TODO: Интеграция с LLM API
        // Пример для YandexGPT:
        // return yandexGptService.complete(prompt);
        aiGateway.sendMessageToModel(prompt);

        return "[Заглушка] Ответ будет сгенерирован LLM. Контекст: " + prompt.substring(0, 200) + "...";
    }
}