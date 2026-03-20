package ru.core.profilems.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.core.profilems.controller.ProfilePhotoController;
import ru.core.profilems.dto.PhotoMetaInfoDto;
import ru.core.profilems.mapper.PhotoMapper;
import ru.core.profilems.model.PhotoWithUrl;
import ru.core.profilems.model.entity.PhotoMetaInfo;
import ru.core.profilems.service.business.PhotoManager;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfilePhotoControllerImpl implements ProfilePhotoController {

    private final PhotoManager photoManager;
    private final PhotoMapper photoMapper;

    @Override
    public PhotoMetaInfoDto getByProfileId(UUID profileId) {
        PhotoWithUrl photoMetaWithUrl = photoManager.getPhotoByProfileId(profileId);

        return photoMapper.convert(photoMetaWithUrl);
    }

    @Override
    public PhotoMetaInfoDto uploadPhoto(UUID profileId, MultipartFile photo) {
        PhotoMetaInfo photoMetaInfo = photoManager.createPhoto(profileId, photo);

        return photoMapper.convert(photoMetaInfo);
    }

    @Override
    public PhotoMetaInfoDto updatePhoto(UUID profileId, MultipartFile photo) {
        PhotoMetaInfo photoMetaInfo = photoManager.updatePhoto(profileId, photo);

        return photoMapper.convert(photoMetaInfo);
    }

    @Override
    public void deletePhoto(UUID profileId) {
        photoManager.deletePhotoByProfileId(profileId);
    }
}
