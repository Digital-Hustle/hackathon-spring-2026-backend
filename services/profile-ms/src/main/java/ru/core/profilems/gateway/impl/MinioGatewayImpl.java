package ru.core.profilems.gateway.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.core.profilems.config.properties.AppMinioProperties;
import ru.core.profilems.constants.PhotoConstants;
import ru.core.profilems.constants.PhotoErrorMessages;
import ru.core.profilems.exception.exception.PhotoProcessingException;
import ru.core.profilems.gateway.MinioGateway;

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
    public void savePhoto(String absoluteFilePath, MultipartFile photo) {
        try (InputStream dataStream = photo.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(absoluteFilePath)
                            .stream(dataStream, photo.getSize(), PhotoConstants.USE_DEFAULT_PART_SIZE)
                            .contentType(photo.getContentType())
                            .build()
            );
        } catch (IOException | MinioException | NoSuchAlgorithmException | InvalidKeyException exception) {
            log.error("Error uploading photo: {}", exception.getMessage());
            throw new PhotoProcessingException(PhotoErrorMessages.FAILED_TO_UPLOAD_PHOTO);
        }
    }

    @Override
    public void deletePhoto(String absoluteFilePath) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(appMinioProperties.getBucketName())
                            .object(absoluteFilePath)
                            .build()
            );
        } catch (IOException | MinioException | NoSuchAlgorithmException | InvalidKeyException exception) {
            throw new PhotoProcessingException(PhotoErrorMessages.FAILED_TO_DELETE_PHOTO);
        }
    }
}
