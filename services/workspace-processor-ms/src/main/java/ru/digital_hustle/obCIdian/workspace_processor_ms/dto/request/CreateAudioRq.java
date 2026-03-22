package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record CreateAudioRq(
        UUID workspaceId
) {
}