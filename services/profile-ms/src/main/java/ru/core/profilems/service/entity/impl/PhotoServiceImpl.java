package ru.core.profilems.service.entity.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.core.profilems.gateway.MinioGateway;
import ru.core.profilems.service.entity.PhotoService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final MinioGateway minioGateway;

    @Override
    public void save(String absoluteFilePath, MultipartFile photo) {
        minioGateway.savePhoto(absoluteFilePath, photo);
    }

    @Override
    public void delete(String absoluteFilePath) {
        minioGateway.deletePhoto(absoluteFilePath);
    }
}
