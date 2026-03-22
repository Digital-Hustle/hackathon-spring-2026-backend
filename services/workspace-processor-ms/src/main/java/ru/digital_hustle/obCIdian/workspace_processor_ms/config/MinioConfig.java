package ru.digital_hustle.obCIdian.workspace_processor_ms.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.digital_hustle.obCIdian.workspace_processor_ms.config.properties.AppMinioProperties;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.minio.enabled", havingValue = "true")
public class MinioConfig {

    private final AppMinioProperties appMinioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(appMinioProperties.getEndpoint())
                .credentials(appMinioProperties.getAccessKey(), appMinioProperties.getSecretKey())
                .build();
    }

    @Bean
    public InitializingBean minioBucketInitializer(MinioClient minioClient) {
        return () -> {
            try {
                String bucketName = appMinioProperties.getBucketName();
                createBucket(minioClient, bucketName);

            } catch (Exception exception) {
                log.error("Failed to initialize MinIO bucket: {}", exception.getMessage(), exception);
                throw new RuntimeException("Error");
            }
        };
    }

    private void createBucket(MinioClient minioClient, String bucketName) throws Exception {
        boolean bucketExists = bucketExists(minioClient, bucketName);

        if (!bucketExists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            log.info("Bucket '{}' successfully created", bucketName);
        } else {
            log.info("Bucket '{}' already exists", bucketName);
        }
    }

    private boolean bucketExists(MinioClient minioClient, String bucketName) throws Exception {
        return minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );
    }
}
