package ru.core.profilems.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PhotoErrorMessages {
    public static final String INVALID_PHOTO_EXTENSION = "Invalid photo extension";
    public static final String INVALID_OWNER_TYPE = "Invalid photo owner type";
    public static final String FAILED_TO_UPLOAD_PHOTO = "Failed to upload photo";
    public static final String FAILED_TO_DELETE_PHOTO = "Failed to delete photo";
}
