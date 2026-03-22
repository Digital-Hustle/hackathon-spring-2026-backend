package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class YandexGptCompletionRequest {
    private String modelUri;
    private CompletionOptions completionOptions;
    private List<Message> messages;

    @Data
    @AllArgsConstructor
    public static class CompletionOptions {
        private double temperature;
        private int maxTokens;
    }

    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String text;
    }
}