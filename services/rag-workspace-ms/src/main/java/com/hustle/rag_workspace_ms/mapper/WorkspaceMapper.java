package com.hustle.rag_workspace_ms.mapper;

import com.hustle.rag_workspace_ms.dto.WorkspaceDto;
import com.hustle.rag_workspace_ms.dto.request.UpdateWorkspaceRq;
import com.hustle.rag_workspace_ms.model.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkspaceMapper {

    WorkspaceDto convert(Workspace workspace);

    Workspace convert(UpdateWorkspaceRq workspace);

    default Page<WorkspaceDto> convert(Page<Workspace> source) {
        return source.map(this::convert);
    }
}
