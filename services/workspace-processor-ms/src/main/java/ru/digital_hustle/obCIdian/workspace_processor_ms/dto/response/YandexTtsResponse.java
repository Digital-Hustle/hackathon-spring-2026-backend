package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response;

public record YandexTtsResponse(
        Result result
) {
    public record Result(AudioChunk audioChunk) {}

    public record AudioChunk(String data) {}
}