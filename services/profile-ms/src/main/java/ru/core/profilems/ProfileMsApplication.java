package ru.core.profilems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.core.profilems.config.properties.AppMinioProperties;

@EnableConfigurationProperties(AppMinioProperties.class)
@EnableWebSecurity
@EnableFeignClients
@EnableMethodSecurity
@SpringBootApplication
public class ProfileMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileMsApplication.class, args);
    }

}
