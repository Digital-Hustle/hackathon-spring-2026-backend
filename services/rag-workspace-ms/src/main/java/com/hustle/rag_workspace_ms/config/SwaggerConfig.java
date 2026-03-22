package com.hustle.rag_workspace_ms.config;

import com.hustle.rag_workspace_ms.constants.AppConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().addList(AppConstants.BEARER_AUTH)
                )
                .components(securityComponent())
                .info(info());
    }

    private Components securityComponent() {
        return new Components()
                .addSecuritySchemes(
                        AppConstants.BEARER_AUTH,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }

    private Info info() {
        return new Info()
                .title("Task Manager: Profile")
                .version("1.0")
                .description("Microservices based Spring Boot application for task management");
    }
}
