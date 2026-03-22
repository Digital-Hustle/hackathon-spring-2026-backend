package ru.digital_hustle.obCIdian.workspace_processor_ms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.digital_hustle.obCIdian.workspace_processor_ms.controller.AudioController;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request.CreateAudioRq;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.AudioRs;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.GetAudioRetelling;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.entity.AudioStorageService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AudioControllerImpl implements AudioController {

    private final GetAudioRetelling getAudioRetelling;
    private final AudioStorageService audioStorageService;

    @Override
    public AudioRs createAudio(CreateAudioRq request) {
        return getAudioRetelling.getAudio(request.workspaceId());
    }

    @Override
    public AudioRs getAudio(UUID workspaceId) {
        return audioStorageService.getAudio(workspaceId);
    }
}