package com.hustle.rag_workspace_ms.mapper;

import com.hustle.rag_workspace_ms.dto.request.CreateNoteRq;
import com.hustle.rag_workspace_ms.dto.response.NotePreviewRs;
import com.hustle.rag_workspace_ms.dto.response.NoteRs;
import com.hustle.rag_workspace_ms.mapper.util.JsonNodeMapper;
import com.hustle.rag_workspace_ms.model.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = JsonNodeMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contentJson", source = "contentJson", qualifiedByName = "jsonNodeToString")
    Note toEntity(CreateNoteRq rq);

    @Mapping(target = "contentJson", source = "contentJson", qualifiedByName = "stringToJsonNode")
    NoteRs toDto(Note note);

    NotePreviewRs toPreviewDto(Note note);
}