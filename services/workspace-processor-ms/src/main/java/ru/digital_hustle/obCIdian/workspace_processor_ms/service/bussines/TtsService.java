package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines;

public interface TtsService {
    byte[] synthesize(String text, String speaker);
}