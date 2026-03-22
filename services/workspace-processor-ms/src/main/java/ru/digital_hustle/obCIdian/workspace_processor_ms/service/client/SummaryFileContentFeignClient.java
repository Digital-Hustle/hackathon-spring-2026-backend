package ru.digital_hustle.obCIdian.workspace_processor_ms.service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentText;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentTextRs;

import java.util.UUID;

@Profile("!test")
@FeignClient(
        name= "fileStorageClient",
        url = "http://192.168.0.104:4002"
)
public interface SummaryFileContentFeignClient {

    @GetMapping("/api/v1/workspaces/{workspaceId}/documents/content")
    DocumentTextRs getDocuments(@PathVariable("workspaceId") UUID workspaceId);
}
