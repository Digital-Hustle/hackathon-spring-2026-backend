package com.hustle.rag_workspace_ms.mapper;

import com.hustle.rag_workspace_ms.dto.response.DocumentMetaRs;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

// TODO вернуть общий кофиг
//@Mapper(config = BaseMapperConfig.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMetaMapper {

    DocumentMetaRs convert(DocumentMeta source);
}
