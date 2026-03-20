package ru.core.profilems.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.core.profilems.constants.SecurityConstants;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().addList(SecurityConstants.BEARER_AUTH)
                )
                .components(securityComponent())
                .info(info());
    }

    private Components securityComponent() {
        return new Components()
                .addSecuritySchemes(
                        SecurityConstants.BEARER_AUTH,
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
