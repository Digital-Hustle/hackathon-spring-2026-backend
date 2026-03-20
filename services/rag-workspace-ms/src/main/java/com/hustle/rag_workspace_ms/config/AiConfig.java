package com.hustle.rag_workspace_ms.config;

import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO Ладншафт доступен по адресу https://hackai.centrinvest.ru:
//  - порт 6620 - Эмбеддер (векторизация документов и текстов)
//  - порт 6630 - LLM
//  - порт 6640 - STT (распознование текста из аудио)
//  API ключ для доступа к моделям, hackaton2026
@Configuration
public class AiConfig {

    @Bean
    public TextSplitter textSplitter() {
        return TokenTextSplitter.builder()
                .withChunkSize(500)
                .withMinChunkSizeChars(350)
                .withMinChunkLengthToEmbed(5)
                .withMaxNumChunks(10000)
                .withKeepSeparator(true)
                .build();
    }
}