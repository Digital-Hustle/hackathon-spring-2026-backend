package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.Utterance;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.AudioRs;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentText;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentsTextRs;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.*;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.entity.AudioStorageService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAudioRetellingImpl implements GetAudioRetelling {

    private final GetDocumentsContentService getDocumentsContentService;
    private final DialogueScriptService dialogueScriptService;
    private final DialogueAudioRenderService dialogueAudioRenderService;
    private final AudioMetadataService audioMetadataService;
    private final AudioStorageService audioStorageService;

    @Override
    public AudioRs getAudio(UUID workspaceId) {
        DocumentsTextRs docsResponse = getDocumentsContentService.getContent(workspaceId);
        List<DocumentText> documents = docsResponse.documents();

        String combinedText = documents.stream()
                .map(DocumentText::content)
                .collect(Collectors.joining("\n\n"));

        String style = "популярный";

        List<Utterance> script = dialogueScriptService.generateScript(combinedText, style);

        log.info("Сгенерированный сценарий {}:", script.size());
        for (Utterance u : script) {
            log.info("{}: {}", u.speaker(), u.text());
        }

        byte[] wav = dialogueAudioRenderService.renderDialogue(script);
        Double duration = audioMetadataService.getDurationSeconds(wav);

        String objectName = audioStorageService.saveAudio(workspaceId, wav, duration);

        log.info("WAV сохранен в MinIO: {}, duration={} sec", objectName, duration);

        return AudioRs.builder()
                .url(objectName)
                .duration(duration)
                .build();
    }
}