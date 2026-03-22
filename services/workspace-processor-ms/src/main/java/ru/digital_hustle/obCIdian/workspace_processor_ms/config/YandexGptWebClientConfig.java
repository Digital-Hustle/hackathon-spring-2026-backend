package ru.digital_hustle.obCIdian.workspace_processor_ms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class YandexGptWebClientConfig {

    @Value("${yandex.gpt.api-key:#{null}}")
    private String apiKey;

    @Value("${yandex.gpt.base-url:https://ai.api.cloud.yandex.net/v1}")
    private String baseUrl;

    @Bean
    public WebClient yandexGptWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", resolveAuthHeader())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    private String resolveAuthHeader() {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("Yandex GPT API key is not configured");
        }

        if (apiKey.startsWith("t1.") || apiKey.startsWith("y2.")) {
            return "Bearer " + apiKey;
        }

        return "Api-Key " + apiKey;
    }
}