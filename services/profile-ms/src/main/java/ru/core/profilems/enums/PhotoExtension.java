package ru.core.profilems.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PhotoExtension {
    PNG("png"),
    JPEG("jpeg"),
    JPG("jpg");

    private final String extension;
}
