package com.hustle.rag_workspace_ms;

import com.hustle.rag_workspace_ms.config.properties.AppMinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableFeignClients
@EnableConfigurationProperties(AppMinioProperties.class)
@SpringBootApplication
public class RagWorkspaceMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RagWorkspaceMsApplication.class, args);
    }

}
