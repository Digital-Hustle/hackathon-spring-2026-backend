package com.hustle.rag_workspace_ms.dto.response;

import com.hustle.rag_workspace_ms.dto.MessageDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder(toBuilder = true)
public record ChatRs(

        Page<MessageDto> messages
) {
}
