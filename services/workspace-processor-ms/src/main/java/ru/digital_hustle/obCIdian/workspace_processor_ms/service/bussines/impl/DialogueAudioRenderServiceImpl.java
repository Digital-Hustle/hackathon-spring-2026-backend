package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.AudioChunk;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.Utterance;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.AudioMergeService;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.DialogueAudioRenderService;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.TtsService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
public class DialogueAudioRenderServiceImpl implements DialogueAudioRenderService {

    private final TtsService ttsService;
    private final AudioMergeService audioMergeService;
    private final Executor ttsExecutor;

    @Override
    public byte[] renderDialogue(List<Utterance> script) {
        List<CompletableFuture<AudioChunk>> futures = new ArrayList<>();

        for (int i = 0; i < script.size(); i++) {
            Utterance utterance = script.get(i);
            int order = i;

            futures.add(
                    CompletableFuture.supplyAsync(() -> {
                        byte[] wav = ttsService.synthesize(
                                utterance.text(),
                                utterance.speaker()
                        );
                        return new AudioChunk(order, utterance.speaker(), wav);
                    }, ttsExecutor)
            );
        }

        List<AudioChunk> chunks = futures.stream()
                .map(CompletableFuture::join)
                .sorted(Comparator.comparingInt(AudioChunk::order))
                .toList();

        return audioMergeService.merge(chunks, 350);
    }
}