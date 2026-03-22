package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines;

import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.AudioChunk;

import java.util.List;

public interface AudioMergeService {
    byte[] merge(List<AudioChunk> chunks, int pauseMs);
}