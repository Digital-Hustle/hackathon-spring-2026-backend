package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines;

import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.AudioRs;

import java.util.UUID;

public interface GetAudioRetelling {
    AudioRs getAudio(UUID workspaceId);
}