package ru.core.profilems.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.core.profilems.model.PhotoWithUrl;
import ru.core.profilems.model.entity.PhotoMetaInfo;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PhotoFactory {

    public static PhotoMetaInfo newDefaultPhotoMetaInfo(MultipartFile file) {
        String fileNameWithExtension = file.getOriginalFilename();

        String fileName = FilenameUtils.getBaseName(fileNameWithExtension);
        String extension = FilenameUtils.getExtension(fileNameWithExtension);

        return PhotoMetaInfo.builder()
                .originalFileName(fileName)
                .extension(extension)
                .fileSize((int) file.getSize())
                .contentType(file.getContentType())
                .build();
    }

    public static PhotoMetaInfo newDefaultPhotoMetaInfoWithProfileId(UUID profileId, MultipartFile file) {
        PhotoMetaInfo photoMetaInfo = newDefaultPhotoMetaInfo(file);

        return photoMetaInfo.toBuilder()
                .profileId(profileId)
                .build();
    }

    public static PhotoWithUrl newDefaultPhotoWithUrl(PhotoMetaInfo photoMetaInfo, String url) {
        return PhotoWithUrl.builder()
                .id(photoMetaInfo.getId())
                .originalFileName(photoMetaInfo.getOriginalFileName())
                .url(url)
                .build();
    }
}
