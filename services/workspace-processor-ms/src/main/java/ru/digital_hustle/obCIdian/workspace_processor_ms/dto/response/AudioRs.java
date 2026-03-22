package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;


@Builder(toBuilder = true)
public record AudioRs(
        String url,
        Double duration
) {
}
