package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response;

import lombok.Builder;
import org.apache.commons.text.translate.UnicodeUnescaper;

import java.util.UUID;

@Builder(toBuilder = true)
public record DocumentText(
        UUID id,
        String content
) {
}
