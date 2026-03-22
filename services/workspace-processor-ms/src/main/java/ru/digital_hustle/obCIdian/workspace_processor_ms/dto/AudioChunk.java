package ru.digital_hustle.obCIdian.workspace_processor_ms.dto;

public record AudioChunk(
        int order,
        String speaker,
        byte[] wavData
) {
}