package ru.digital_hustle.obCIdian.workspace_processor_ms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request.CreateAudioRq;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.AudioRs;

import java.util.UUID;

@RequestMapping("/api/v1/audio")
public interface AudioController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AudioRs createAudio(@RequestBody CreateAudioRq request);

    @GetMapping("/{workspaceId}")
    @ResponseStatus(HttpStatus.OK)
    AudioRs getAudio(@PathVariable("workspaceId") UUID workspaceId);
}