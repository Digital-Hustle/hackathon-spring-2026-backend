package ru.digital_hustle.obCIdian.workspace_processor_ms.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record Utterance(
        String speaker,
        String text
) {
}