package com.hustle.rag_workspace_ms.mapper;

import com.hustle.rag_workspace_ms.dto.DocumentMetaDto;
import com.hustle.rag_workspace_ms.dto.DocumentTextDto;
import com.hustle.rag_workspace_ms.dto.MessageDto;
import com.hustle.rag_workspace_ms.dto.WorkspaceDto;
import com.hustle.rag_workspace_ms.model.DocumentText;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import com.hustle.rag_workspace_ms.model.entity.Message;
import com.hustle.rag_workspace_ms.model.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

// TODO вернуть общий кофиг
//@Mapper(config = BaseMapperConfig.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatMessageMapper {

    MessageDto convert(Message source);

    List<MessageDto> convert(List<Message> source);

    default Page<MessageDto> convert(Page<Message> source) {
        return source.map(this::convert);
    }
}
