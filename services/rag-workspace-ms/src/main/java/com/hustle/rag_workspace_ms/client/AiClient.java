package com.hustle.rag_workspace_ms.client;

import com.hustle.rag_workspace_ms.dto.request.ChatCompletionRq;
import com.hustle.rag_workspace_ms.dto.response.ChatCompletionRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "aiFeignClient",
        url = "${app.ai-api.url}"
)
public interface AiClient {

    @PostMapping("/v1/chat/completions")
    ChatCompletionRs completions(@RequestBody ChatCompletionRq request);
}

