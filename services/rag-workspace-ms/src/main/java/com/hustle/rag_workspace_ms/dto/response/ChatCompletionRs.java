package com.hustle.rag_workspace_ms.dto.response;

import com.hustle.rag_workspace_ms.dto.ChoiceDto;

import java.util.List;

public record ChatCompletionRs(

        List<ChoiceDto> choices
) {
}
