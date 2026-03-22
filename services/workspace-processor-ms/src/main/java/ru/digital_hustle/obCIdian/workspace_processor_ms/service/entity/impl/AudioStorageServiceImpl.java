package ru.digital_hustle.obCIdian.workspace_processor_ms.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.AudioRs;
import ru.digital_hustle.obCIdian.workspace_processor_ms.gateway.MinioGateway;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.entity.AudioStorageService;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AudioStorageServiceImpl implements AudioStorageService {

    private static final String DURATION_METADATA_KEY = "duration-seconds";

    private final MinioGateway minioGateway;

    @Override
    public String saveAudio(UUID workspaceId, byte[] wavBytes, Double duration) {
        String objectName = buildObjectName(workspaceId);

        Map<String, String> metadata = duration == null
                ? Map.of()
                : Map.of(DURATION_METADATA_KEY, String.valueOf(duration));

        minioGateway.saveAudio(objectName, wavBytes, "audio/wav", metadata);

        return minioGateway.getPresignedDownloadUrl(objectName);
    }

    @Override
    public void deleteAudio(UUID workspaceId) {
        minioGateway.deleteAudio(buildObjectName(workspaceId));
    }

    @Override
    public AudioRs getAudio(UUID workspaceId) {
        String objectName = buildObjectName(workspaceId);

        if (!minioGateway.exists(objectName)) {
            throw new RuntimeException("Audio not found for workspaceId=" + workspaceId);
        }

        Map<String, String> metadata = minioGateway.getObjectMetadata(objectName);

        Double duration = null;
        if (metadata != null && metadata.containsKey(DURATION_METADATA_KEY)) {
            duration = Double.valueOf(metadata.get(DURATION_METADATA_KEY));
        }

        String downloadUrl = minioGateway.getPresignedDownloadUrl(objectName);

        return AudioRs.builder()
                .url(downloadUrl)
                .duration(duration)
                .build();
    }

    private String buildObjectName(UUID workspaceId) {
        return "workspace-audio/" + workspaceId + ".wav";
    }
}