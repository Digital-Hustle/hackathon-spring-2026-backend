package ru.core.profilems.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhotoMetaInfoDto(

        UUID id,

        String originalFileName,

        String extension,

        Integer fileSize,

        String contentType,

        String url
) {
}
