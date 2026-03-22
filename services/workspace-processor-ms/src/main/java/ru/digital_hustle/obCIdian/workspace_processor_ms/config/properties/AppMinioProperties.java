package ru.digital_hustle.obCIdian.workspace_processor_ms.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class AppMinioProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}