package com.hustle.rag_workspace_ms.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record MessageDto(

        String content,

        Boolean ownedByUser
) {
}
