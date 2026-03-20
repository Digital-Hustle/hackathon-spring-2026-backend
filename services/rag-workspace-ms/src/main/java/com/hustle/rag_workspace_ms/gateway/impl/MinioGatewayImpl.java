package com.hustle.rag_workspace_ms.gateway.impl;

import com.hustle.rag_workspace_ms.config.properties.AppMinioProperties;
import com.hustle.rag_workspace_ms.constants.AppConstants;
import com.hustle.rag_workspace_ms.constants.ErrorMessages;
import com.hustle.rag_workspace_ms.exception.custom.DocumentProcessingException;
import com.hustle.rag_workspace_ms.gateway.MinioGateway;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioGatewayImpl implements MinioGateway {

    private final MinioClient minioClient;
    private final AppMinioProperties appMinioProperties;

    @Override
    public InputStream downloadDocument(String minioKey) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(minioKey)
                            .build()
            );
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException exception) {
            log.error("Error uploading photo: {}", exception.getMessage());
            throw new DocumentProcessingException(ErrorMessages.FAILED_TO_GET_DOCUMENT);
        }
    }

    public void saveDocument(String absoluteFilePath, MultipartFile photo) {
        try (InputStream dataStream = photo.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(absoluteFilePath)
                            .stream(dataStream, photo.getSize(), AppConstants.USE_DEFAULT_PART_SIZE)
                            .contentType(photo.getContentType())
                            .build()
            );
        } catch (IOException | MinioException | NoSuchAlgorithmException | InvalidKeyException exception) {
            log.error("Error uploading photo: {}", exception.getMessage());
            throw new DocumentProcessingException(ErrorMessages.FAILED_TO_UPLOAD_DOCUMENT);
        }
    }

    public void deleteDocument(String absoluteFilePath) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(absoluteFilePath)
                            .build()
            );
        } catch (IOException | MinioException | NoSuchAlgorithmException | InvalidKeyException exception) {
            throw new DocumentProcessingException(ErrorMessages.FAILED_TO_DELETE_DOCUMENT);
        }
    }
}
