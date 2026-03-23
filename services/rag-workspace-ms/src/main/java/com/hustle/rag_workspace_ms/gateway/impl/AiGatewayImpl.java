package com.hustle.rag_workspace_ms.gateway.impl;

import com.hustle.rag_workspace_ms.client.AiClient;
import com.hustle.rag_workspace_ms.dto.AiMessageDto;
import com.hustle.rag_workspace_ms.dto.request.ChatCompletionRq;
import com.hustle.rag_workspace_ms.dto.response.ChatCompletionRs;
import com.hustle.rag_workspace_ms.gateway.AiGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiGatewayImpl implements AiGateway {

    private final AiClient aiFeignClient;

    @Value("${app.ai-api.model:gpt-oss-20b}")
    private String model;

    @Override
    public String sendMessageToModel(String chatMessages) {

        AiMessageDto aiMessageDto = AiMessageDto.builder()
                .role("user")
                .content(chatMessages)
                .build();

        ChatCompletionRq chatCompletionRq = ChatCompletionRq.builder()
                .model(model)
                .message(aiMessageDto)
                .build();

        ChatCompletionRs response = aiFeignClient.completions(chatCompletionRq);
        AiMessageDto aiMessage = response.choices().getFirst().message();

        log.info("received response {}", aiMessage);
        return aiMessage.content();
    }
}
