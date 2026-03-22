package ru.digital_hustle.obCIdian.workspace_processor_ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean
    public Executor ttsExecutor() {
        return Executors.newFixedThreadPool(4);
    }
}