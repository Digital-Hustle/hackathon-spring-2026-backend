package ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryItemRs {
    private UUID id;
    private UUID workspaceId;
    private List<UUID> documentIds;
    private String content;
    private LocalDateTime createdAt;
}