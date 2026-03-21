package com.hustle.rag_workspace_ms.mapper;

import com.hustle.rag_workspace_ms.dto.DocumentMetaDto;
import com.hustle.rag_workspace_ms.dto.response.DocumentTextDto;
import com.hustle.rag_workspace_ms.model.DocumentText;
import com.hustle.rag_workspace_ms.model.entity.DocumentMeta;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

// TODO вернуть общий кофиг
//@Mapper(config = BaseMapperConfig.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMetaMapper {

    DocumentMetaDto convert(DocumentMeta source);

    DocumentTextDto convert(DocumentText source);

    List<DocumentMetaDto> convertDocumentMeta(List<DocumentMeta> source);

    List<DocumentTextDto> convertDocumentText(List<DocumentText> source);
}
