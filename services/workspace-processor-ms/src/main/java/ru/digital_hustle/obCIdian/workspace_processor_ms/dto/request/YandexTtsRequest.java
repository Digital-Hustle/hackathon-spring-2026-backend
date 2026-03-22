package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request;

import java.util.List;

public record YandexTtsRequest(
        String text,
        List<Hint> hints,
        OutputAudioSpec outputAudioSpec
) {
    public record Hint(String voice, String role) {}

    public record OutputAudioSpec(ContainerAudio containerAudio) {}

    public record ContainerAudio(String containerAudioType) {}
}