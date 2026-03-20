package ru.core.profilems.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.core.profilems.config.properties.AppMinioProperties;
import ru.core.profilems.constants.PhotoConstants;
import ru.core.profilems.model.entity.PhotoMetaInfo;

@Component
@RequiredArgsConstructor
public final class PhotoHelper {

    private final AppMinioProperties appMinioProperties;

    public String getPhotoUrl(PhotoMetaInfo photoMetaInfo) {
        String hostWithBucket = PhotoConstants.PATH_SEPARATOR
                .formatted(appMinioProperties.getEndpoint(), appMinioProperties.getBucketName());
        String absolutePath = getAbsolutePhotoPath(photoMetaInfo);

        return PhotoConstants.PATH_SEPARATOR.formatted(hostWithBucket, absolutePath);
    }

    public String getAbsolutePhotoPath(PhotoMetaInfo photoMetaInfo) {
        return PhotoConstants.EXTENSION_SEPARATOR
                .formatted(photoMetaInfo.getId(), photoMetaInfo.getExtension());
    }
}
