package ru.digital_hustle.obCIdian.workspace_processor_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
		exclude = {
				JmxAutoConfiguration.class,
		}
)
@EnableFeignClients
public class WorkspaceProcessorMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkspaceProcessorMsApplication.class, args);
	}

}
