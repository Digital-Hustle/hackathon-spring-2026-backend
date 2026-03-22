package com.hustle.rag_workspace_ms.dto.request;

import com.hustle.rag_workspace_ms.constants.ErrorMessages;
import jakarta.validation.constraints.NotNull;

public record SendMessageRq(

        @NotNull(message = "content" + ErrorMessages.IS_REQUIRED)
        String content
) {
}
