package ru.core.profilems.service.business;

import org.springframework.web.multipart.MultipartFile;
import ru.core.profilems.model.PhotoWithUrl;
import ru.core.profilems.model.entity.PhotoMetaInfo;

import java.util.UUID;

public interface PhotoManager {

    PhotoWithUrl getPhotoByProfileId(UUID profileId);

    PhotoMetaInfo createPhoto(UUID profileId, MultipartFile photo);

    PhotoMetaInfo updatePhoto(UUID profileId, MultipartFile photo);

    void deletePhotoByProfileId(UUID id);
}
