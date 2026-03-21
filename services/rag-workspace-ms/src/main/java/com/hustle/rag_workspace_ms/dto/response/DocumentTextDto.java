package com.hustle.rag_workspace_ms.dto.response;

import java.util.UUID;

public record DocumentTextDto(

        UUID id,

        String content
) {
}
