package com.hustle.rag_workspace_ms.dto.request;

import com.hustle.rag_workspace_ms.dto.AiMessageDto;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record ChatCompletionRq(

        String model,

        List<AiMessageDto> messages
) {
}