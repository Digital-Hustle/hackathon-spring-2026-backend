package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryYandexGptRq {
    @JsonProperty("model")
    private String model;

    @JsonProperty("input")
    private String input;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("max_output_tokens")
    private Integer maxOutputTokens;
}