package com.hustle.rag_workspace_ms.controller;

import com.hustle.rag_workspace_ms.client.AiClient;
import com.hustle.rag_workspace_ms.dto.AiMessageDto;
import com.hustle.rag_workspace_ms.dto.request.ChatCompletionRq;
import com.hustle.rag_workspace_ms.dto.response.ChatCompletionRs;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {

    private final AiClient aiFeignClient;

    @Value("${app.ai-api.model:gpt-oss-20b}")
    private String model;

    @GetMapping
    public void test() {
        AiMessageDto aiMessageDto = AiMessageDto.builder()
                .role("user")
                .content("hello, how are you")
                .build();

        ChatCompletionRq chatCompletionRq = ChatCompletionRq.builder()
                .model(model)
                .messages(List.of(aiMessageDto))
                .build();

        ChatCompletionRs rs = aiFeignClient.completions(chatCompletionRq);

        rs.choices().forEach(System.out::println);
    }
}
