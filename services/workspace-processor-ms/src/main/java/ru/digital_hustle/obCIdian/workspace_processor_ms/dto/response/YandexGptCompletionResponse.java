package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class YandexGptCompletionResponse {
    private Result result;

    @Data
    public static class Result {
        private List<Alternative> alternatives;
    }

    @Data
    public static class Alternative {
        private Message message;
    }

    @Data
    public static class Message {
        private String role;
        private String text;
    }
}