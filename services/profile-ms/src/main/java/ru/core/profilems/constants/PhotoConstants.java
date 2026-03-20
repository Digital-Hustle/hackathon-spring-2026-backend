package ru.core.profilems.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PhotoConstants {
    public static final int PHOTO_EXTENSION_LENGTH = 10;
    public static final int CONTENT_TYPE_LENGTH = 100;

    public static final int USE_DEFAULT_PART_SIZE = -1;

    public static final String PATH_SEPARATOR = "%s/%s";
    public static final String EXTENSION_SEPARATOR = "%s.%s";
}
