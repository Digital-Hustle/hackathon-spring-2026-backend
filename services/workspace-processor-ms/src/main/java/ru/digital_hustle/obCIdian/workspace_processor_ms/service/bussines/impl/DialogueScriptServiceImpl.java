package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.Utterance;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.DialogueScriptService;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.YandexGptService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class DialogueScriptServiceImpl implements DialogueScriptService {

    private final ResourceLoader resourceLoader;
    private final YandexGptService yandexGptService;

    @Value("${dialogue.prompt.template.path:classpath:prompt/dialogue_prompt.txt}")
    private String promptTemplatePath;

    @Override
    public List<Utterance> generateScript(String documentText, String style) {
        String prompt = buildPrompt(documentText, style);
        String response = yandexGptService.generateResponse(prompt, 0.7, 500);
        return parseUtterances(response);
    }

    private String buildPrompt(String text, String style) {
        try {
            Resource resource = resourceLoader.getResource(promptTemplatePath);
            String template;
            try (InputStream inputStream = resource.getInputStream()) {
                template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
            log.info(template
                    .replace("{text}", text)
                    .replace("{style}", style));
            return template
                    .replace("{text}", text)
                    .replace("{style}", style);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load prompt template", e);
        }
    }

    private List<Utterance> parseUtterances(String script) {
        List<Utterance> utterances = new ArrayList<>();
        script = script.trim();
        Pattern pattern = Pattern.compile("^(Иван|Анна):\\s*(.+)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(script);
        while (matcher.find()) {
            String speaker = matcher.group(1);
            String text = matcher.group(2).trim();
            utterances.add(new Utterance(speaker, text));
        }
        if (utterances.isEmpty()) {
            utterances.add(new Utterance("Система", "Не удалось разобрать диалог: " + script));
        }
        return utterances;
    }
}