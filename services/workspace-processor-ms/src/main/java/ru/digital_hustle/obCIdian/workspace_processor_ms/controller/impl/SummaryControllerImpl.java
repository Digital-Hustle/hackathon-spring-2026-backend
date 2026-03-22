package ru.digital_hustle.obCIdian.workspace_processor_ms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.digital_hustle.obCIdian.workspace_processor_ms.controller.SummaryController;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request.WorkspaceIdRq;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentText;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentTextRs;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.SummaryItemRs;
import ru.digital_hustle.obCIdian.workspace_processor_ms.model.SummaryEntity;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.SummaryYandexGptService;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.client.SummaryFileContentFeignClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summary")
public class SummaryControllerImpl implements SummaryController {

    private final SummaryYandexGptService summaryYandexGptService;
    private final SummaryFileContentFeignClient fileContentFeignClient;

    private static final double TEMPERATURE = 0.3;
    private static final int MAX_TOKENS = 1000;

    @Override
    @PostMapping("/generate")
    public Mono<ResponseEntity<String>> generateSummary(@RequestBody WorkspaceIdRq request) {

        if (request.getWorkspaceId() == null) {
            return Mono.just(ResponseEntity.badRequest().body("workspaceId не может быть пустым"));
        }

        DocumentTextRs response = fileContentFeignClient.getDocuments(request.getWorkspaceId());
        List<DocumentText> documents = response.getDocuments();
        System.out.println(documents);

        if (documents == null || documents.isEmpty()) {
            return Mono.just(ResponseEntity.status(503).body("Не удалось загрузить контент из документов"));
        }

        List<UUID> realDocumentIds = documents.stream()
                .map(DocumentText::getId)
                .collect(Collectors.toList());

        String combinedText = documents.stream()
                .map(DocumentText::getContent)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining("\n\n---\n\n"));

        if (combinedText.isBlank()) {
            return Mono.just(ResponseEntity.status(503).body("Не удалось загрузить контент из документов"));
        }

        String prompt = buildPrompt(combinedText);

        return summaryYandexGptService.generateAndSaveSummaryAsync(
                        request.getWorkspaceId(),
                        realDocumentIds,
                        prompt,
                        TEMPERATURE,
                        MAX_TOKENS)
                .map(ResponseEntity::ok)
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(500)
                                .body("Ошибка генерации: " + e.getMessage())));
    }

    @Override
    @GetMapping("/{workspaceId}")
    public ResponseEntity<List<SummaryItemRs>> getSummariesByWorkspace(@PathVariable UUID workspaceId) {

        List<SummaryEntity> summaries = summaryYandexGptService.getSummariesByWorkspaceId(workspaceId);

        List<SummaryItemRs> result = summaries.stream()
                .map(entity -> SummaryItemRs.builder()
                        .id(entity.getId())
                        .workspaceId(entity.getWorkspaceId())
                        .documentIds(entity.getDocumentIds())
                        .content(entity.getContent())
                        .createdAt(entity.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    private String buildPrompt(String text) {
        return """
                # РОЛЬ
                Ты — старший бизнес-аналитик и профессиональный секретарь. Твоя задача — обрабатывать большие объемы текстовой информации и составлять по ним структурированные резюме (саммари) в строгом официально-деловом стиле.
                                
                # ЗАДАЧА
                Проанализируй предоставленный ниже текст и составь его краткое содержание.
                                
                # ТРЕБОВАНИЯ К СТИЛЮ
                1. Тон: Нейтральный, формальный, объективный.
                2. Лексика: Деловая терминология, отсутствие разговорных выражений, сленга и эмоциональных окрасок.
                3. Форма: Без использования местоимений «я», «мы» (если это не прямая цитата), предпочтительно безличное изложение или пассивный залог.
                4. Запрещено: Добавлять от себя мнения, выводы, которых нет в тексте, или использовать эмодзи.
                5. Не нужно добавлять лишние символы, например: "#" или "*"
                                
                # СТРУКТУРА ОТВЕТА
                Твое резюме должно строго следовать этому формату:
                                
                1. Общая суть
                (Один-два абзаца. О чем документ/переписка? Какова основная цель?)
                                
                2. Ключевые тезисы
                (Маркированный список. Только факты, цифры, даты и важные утверждения. Максимум 5-7 пунктов.)
                                
                3. Принятые решения и договоренности
                (Что утверждено? Какие сроки установлены? Кто ответственный?)
                                
                4. Вопросы на контроле / Риски
                (Есть ли нерешенные проблемы, дедлайны или потенциальные угрозы, упомянутые в тексте?)
                                
                # ВХОДНОЙ ТЕКСТ
                %s
                # НАЧАЛО ГЕНЕРАЦИИ
                Сгенерируй резюме, следуя инструкции выше. Не пиши вступительных фраз вроде «Вот ваше резюме». Начинай сразу с заголовка.
                """.formatted(text != null && !text.isBlank() ? text : "[Пустой текст]");
    }
}
