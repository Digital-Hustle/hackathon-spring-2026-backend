package ru.digital_hustle.obCIdian.workspace_processor_ms.gateway.impl;

import io.minio.*;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digital_hustle.obCIdian.workspace_processor_ms.config.properties.AppMinioProperties;
import ru.digital_hustle.obCIdian.workspace_processor_ms.gateway.MinioGateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioGatewayImpl implements MinioGateway {

    private final MinioClient minioClient;
    private final AppMinioProperties appMinioProperties;

    @Override
    public void saveAudio(String objectName, byte[] data, String contentType, Map<String, String> metadata) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(objectName)
                            .stream(inputStream, data.length, -1)
                            .contentType(contentType)
                            .userMetadata(metadata)
                            .build()
            );
        } catch (IOException | MinioException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error uploading audio to MinIO: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to upload audio to MinIO", e);
        }
    }

    @Override
    public void deleteAudio(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(objectName)
                            .build()
            );
        } catch (IOException | MinioException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error deleting audio from MinIO: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete audio from MinIO", e);
        }
    }

    @Override
    public boolean exists(String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<String, String> getObjectMetadata(String objectName) {
        try {
            StatObjectResponse response = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(objectName)
                            .build()
            );
            return response.userMetadata();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get object metadata from MinIO", e);
        }
    }

    public String getPresignedDownloadUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(appMinioProperties.getBucketName())
                            .object(objectName)
                            .expiry(60 * 60)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create presigned URL for object=" + objectName, e);
        }
    }
}