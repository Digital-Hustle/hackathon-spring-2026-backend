package com.hustle.rag_workspace_ms.dto.response;

import com.hustle.rag_workspace_ms.dto.WorkspaceDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder(toBuilder = true)
public record WorkspacesRs(

        Page<WorkspaceDto> workspacesPage
) {
}
