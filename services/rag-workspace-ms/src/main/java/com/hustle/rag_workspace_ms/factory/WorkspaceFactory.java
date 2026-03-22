package com.hustle.rag_workspace_ms.factory;

import com.hustle.rag_workspace_ms.enums.WorkspaceCardType;
import com.hustle.rag_workspace_ms.model.entity.Workspace;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WorkspaceFactory {

    public static Workspace newDefaultWorkspace(UUID id) {

        return Workspace.builder()
                .id(id)
                .title("Undefined")
                .isFavourite(false)
                .cardType(WorkspaceCardType.MEDIUM)
                .build();
    }
}
