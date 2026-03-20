package ru.core.profilems.gateway;

import org.springframework.web.multipart.MultipartFile;

public interface MinioGateway {

    void savePhoto(String absoluteFilePath, MultipartFile photo);

    void deletePhoto(String absoluteFilePath);
}
