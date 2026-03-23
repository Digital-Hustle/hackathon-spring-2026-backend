package com.hustle.rag_workspace_ms.mapper;

import com.hustle.rag_workspace_ms.dto.AiMessageDto;
import com.hustle.rag_workspace_ms.model.AiMessage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

// TODO вернуть общий кофиг
//@Mapper(config = BaseMapperConfig.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AiMessageMapper {

    AiMessageDto convert(AiMessage source);

    List<AiMessageDto> convert(List<AiMessage> source);
}
