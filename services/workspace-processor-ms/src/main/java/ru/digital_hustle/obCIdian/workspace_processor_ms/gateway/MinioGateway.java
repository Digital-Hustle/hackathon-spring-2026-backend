package ru.digital_hustle.obCIdian.workspace_processor_ms.gateway;

import java.util.Map;

public interface MinioGateway {
    void saveAudio(String objectName, byte[] data, String contentType, Map<String, String> metadata);
    void deleteAudio(String objectName);
    boolean exists(String objectName);
    Map<String, String> getObjectMetadata(String objectName);
    String getPresignedDownloadUrl(String objectName);
}