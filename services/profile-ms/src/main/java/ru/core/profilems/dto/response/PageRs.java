package ru.core.profilems.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
@JsonPropertyOrder({
        "content", "totalPages", "totalElements",
        "curPage", "pageSize"
})
@Schema(description = "Page response")
public record PageRs<T>(

        @Schema(description = "Page content. List of objects", type = "List")
        List<T> content,

        @Schema(description = "Total pages amount", example = "5", type = "Integer")
        int totalPages,

        @Schema(description = "Total elements amount", example = "25", type = "Long")
        long totalElements,

        @Schema(description = "Current page number", example = "3", type = "Integer")
        int curPage,

        @Schema(description = "Amount of elements for each page", example = "5", type = "Integer")
        int pageSize
) {
}
