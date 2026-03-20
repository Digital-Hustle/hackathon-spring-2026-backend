package ru.core.profilems.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.core.profilems.config.properties.AppMinioProperties;
import ru.core.profilems.constants.ErrorMessages;
import ru.core.profilems.exception.exception.BucketCreationException;

@Slf4j
@Configuration
@RequiredArgsConstructor
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
                throw new BucketCreationException(ErrorMessages.FAILED_TO_CREATE_BUCKET);
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
