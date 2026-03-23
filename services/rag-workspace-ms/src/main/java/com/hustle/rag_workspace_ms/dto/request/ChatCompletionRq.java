package com.hustle.rag_workspace_ms.dto.request;

import com.hustle.rag_workspace_ms.dto.AiMessageDto;
import lombok.Builder;

@Builder(toBuilder = true)
public record ChatCompletionRq(

        String model,

        AiMessageDto message
) {
}