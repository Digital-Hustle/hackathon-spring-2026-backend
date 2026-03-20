package ru.core.profilems.validator;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.core.profilems.constants.ErrorMessages;
import ru.core.profilems.constants.PhotoErrorMessages;
import ru.core.profilems.enums.PhotoExtension;
import ru.core.profilems.exception.exception.InvalidPhotoExtension;
import ru.core.profilems.exception.exception.ProfilePhotoAlreadyExists;

@Component
public final class PhotoValidator {

    public void checkPhotoExtension(MultipartFile photo) {
        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());

        if (extension == null) {
            throw new InvalidPhotoExtension(PhotoErrorMessages.INVALID_PHOTO_EXTENSION);
        }

        try {
            PhotoExtension.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidPhotoExtension(PhotoErrorMessages.INVALID_PHOTO_EXTENSION);
        }
    }

    public void checkOwnerExistence(boolean ownerExists) {
        if (!ownerExists) {
            throw new EntityNotFoundException(ErrorMessages.PHOTO_OWNER_NOT_FOUND);
        }
    }

    public void checkProfilePhotoNonExistence(boolean photoExists) {
        if (photoExists) {
            throw new ProfilePhotoAlreadyExists(ErrorMessages.PROFILE_PHOTO_ALREADY_EXISTS);
        }
    }
}
