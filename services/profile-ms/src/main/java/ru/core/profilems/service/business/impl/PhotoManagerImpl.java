package ru.core.profilems.service.business.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.core.profilems.factory.PhotoFactory;
import ru.core.profilems.helper.PhotoHelper;
import ru.core.profilems.model.PhotoWithUrl;
import ru.core.profilems.model.entity.PhotoMetaInfo;
import ru.core.profilems.service.business.PhotoManager;
import ru.core.profilems.service.entity.PhotoMetaInfoService;
import ru.core.profilems.service.entity.PhotoService;
import ru.core.profilems.service.entity.ProfileService;
import ru.core.profilems.validator.PhotoValidator;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoManagerImpl implements PhotoManager {

    private final PhotoService photoService;
    private final ProfileService profileService;
    private final PhotoMetaInfoService photoMetaInfoService;
    private final PhotoValidator photoValidator;
    private final PhotoHelper photoHelper;

    @Override
    public PhotoWithUrl getPhotoByProfileId(UUID profileId) {
        PhotoMetaInfo photoMetaInfo = photoMetaInfoService.getByOwnerId(profileId);
        String url = photoHelper.getPhotoUrl(photoMetaInfo);

        return PhotoFactory.newDefaultPhotoWithUrl(photoMetaInfo, url);
    }

    @Transactional
    @Override
    public PhotoMetaInfo createPhoto(UUID profileId, MultipartFile photo) {
        photoValidator.checkPhotoExtension(photo);

        boolean ownerExists = profileService.exists(profileId);
        photoValidator.checkOwnerExistence(ownerExists);

        boolean profilePhotoAlreadyExists = photoMetaInfoService.existsByProfileId(profileId);
        photoValidator.checkProfilePhotoNonExistence(profilePhotoAlreadyExists);

        PhotoMetaInfo photoMetaInfo = PhotoFactory.newDefaultPhotoMetaInfoWithProfileId(profileId, photo);
        PhotoMetaInfo savedPhotoMeta = photoMetaInfoService.create(photoMetaInfo);

        String absolutePhotoPath = photoHelper.getAbsolutePhotoPath(savedPhotoMeta);
        photoService.save(absolutePhotoPath, photo);

        return savedPhotoMeta;
    }

    @Transactional
    @Override
    public PhotoMetaInfo updatePhoto(UUID profileId, MultipartFile photo) {
        photoValidator.checkPhotoExtension(photo);

        boolean ownerExists = profileService.exists(profileId);
        photoValidator.checkOwnerExistence(ownerExists);

        PhotoMetaInfo photoMetaInfo = PhotoFactory.newDefaultPhotoMetaInfoWithProfileId(profileId, photo);

        UUID existedPhotoMetaInfoId = photoMetaInfoService.getByOwnerId(profileId).getId();
        PhotoMetaInfo updatedPhotoMetaInfo = photoMetaInfoService.update(existedPhotoMetaInfoId, photoMetaInfo);

        String absolutePhotoPath = photoHelper.getAbsolutePhotoPath(updatedPhotoMetaInfo);
        photoService.save(absolutePhotoPath, photo);

        return updatedPhotoMetaInfo;
    }

    @Transactional
    @Override
    public void deletePhotoByProfileId(UUID profileId) {
        try {
            PhotoMetaInfo photoMetaInfo = photoMetaInfoService.getByOwnerId(profileId);
            String absolutePhotoPath = photoHelper.getAbsolutePhotoPath(photoMetaInfo);

            photoMetaInfoService.delete(photoMetaInfo.getId());
            photoService.delete(absolutePhotoPath);
        } catch (EntityNotFoundException exception) {
            log.debug("Photo for profile with id {} not found, deletion skipped", profileId);
        }
    }
}
