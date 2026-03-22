package ru.digital_hustle.obCIdian.workspace_processor_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.digital_hustle.obCIdian.workspace_processor_ms.config.properties.AppMinioProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppMinioProperties.class)
@EnableFeignClients
public class WorkspaceProcessorMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkspaceProcessorMsApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
