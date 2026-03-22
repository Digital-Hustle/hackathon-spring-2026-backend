package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines;

public interface AudioMetadataService {
    Double getDurationSeconds(byte[] wavBytes);
}