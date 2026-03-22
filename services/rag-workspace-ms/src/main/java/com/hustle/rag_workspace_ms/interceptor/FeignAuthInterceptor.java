package com.hustle.rag_workspace_ms.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    @Value("${app.ai-api.key}")
    private String apiToken;

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer " + apiToken);
    }
}