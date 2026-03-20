package ru.core.profilems.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProfileConstants {
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;

    public static final int MIN_SURNAME_LENGTH = 2;
    public static final int MAX_SURNAME_LENGTH = 100;
}
