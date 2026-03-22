package ru.digital_hustle.obCIdian.workspace_processor_ms.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentsTextRs;

import java.util.UUID;

@FeignClient(
        name = "contentFeignClient",
        url = "${services.rag.url}"
)
public interface ContentFeignClient {

    @GetMapping("/api/v1/workspaces/{workspaceId}/documents/content")
    DocumentsTextRs getDocumentsContent(@PathVariable("workspaceId") UUID workspaceId);
}