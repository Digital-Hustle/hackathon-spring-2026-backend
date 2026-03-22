package ru.digital_hustle.obCIdian.workspace_processor_ms.service.entity;

import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.AudioRs;

import java.util.UUID;

public interface AudioStorageService {
    String saveAudio(UUID workspaceId, byte[] wavBytes, Double duration);
    void deleteAudio(UUID workspaceId);
    AudioRs getAudio(UUID workspaceId);
}