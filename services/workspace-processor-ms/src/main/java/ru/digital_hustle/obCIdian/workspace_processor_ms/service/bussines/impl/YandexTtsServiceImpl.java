package ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request.YandexTtsRequest;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.YandexTtsResponse;
import ru.digital_hustle.obCIdian.workspace_processor_ms.service.bussines.TtsService;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YandexTtsServiceImpl implements TtsService {

    private static final String TTS_URL =
            "https://tts.api.cloud.yandex.net/tts/v3/utteranceSynthesis";

    @Value("${yandex.tts.iam-token}")
    private String iamToken;

    @Value("${yandex.tts.folder-id}")
    private String folderId;

    private final RestTemplate restTemplate;

    @Override
    public byte[] synthesize(String text, String speaker) {
        YandexTtsRequest request = new YandexTtsRequest(
                text,
                buildHints(speaker),
                new YandexTtsRequest.OutputAudioSpec(
                        new YandexTtsRequest.ContainerAudio("WAV")
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(iamToken);
        headers.set("x-folder-id", folderId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<YandexTtsRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<YandexTtsResponse> response = restTemplate.exchange(
                TTS_URL,
                HttpMethod.POST,
                entity,
                YandexTtsResponse.class
        );

        if (response.getBody() == null
                || response.getBody().result() == null
                || response.getBody().result().audioChunk() == null
                || response.getBody().result().audioChunk().data() == null) {
            throw new IllegalStateException("Empty TTS response");
        }

        return Base64.getDecoder().decode(
                response.getBody().result().audioChunk().data()
        );
    }

    private List<YandexTtsRequest.Hint> buildHints(String speaker) {
        if ("Иван".equals(speaker)) {
            return List.of(
                    new YandexTtsRequest.Hint("ermil", null),
                    new YandexTtsRequest.Hint(null, "good")
            );
        }

        return List.of(
                new YandexTtsRequest.Hint("marina", null),
                new YandexTtsRequest.Hint(null, "friendly")
        );
    }
}