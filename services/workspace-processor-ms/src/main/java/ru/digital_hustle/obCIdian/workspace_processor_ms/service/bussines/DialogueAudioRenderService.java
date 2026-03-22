package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines;

import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.Utterance;

import java.util.List;

public interface DialogueAudioRenderService {
    byte[] renderDialogue(List<Utterance> script);
}