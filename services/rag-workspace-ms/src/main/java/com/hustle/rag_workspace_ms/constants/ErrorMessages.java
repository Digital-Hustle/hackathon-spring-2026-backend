package com.hustle.rag_workspace_ms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// TODO убрать лишние
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {

    public static final String IS_REQUIRED = " is required";

    public static final String PROFILE_NOT_FOUND = "Profile with id '%s' not found";
    public static final String PROFILE_ALREADY_EXISTS = "Profile with id '%s' already exists";

    public static final String FAILED_TO_CREATE_BUCKET = "MinIO bucket initialization failed";
    public static final String FAILED_TO_GET_DOCUMENT = "Failed to get document";
    public static final String FAILED_TO_UPLOAD_DOCUMENT = "Failed to upload document";
    public static final String FAILED_TO_DELETE_DOCUMENT = "Failed to delete document";

    public static final String PHOTO_OWNER_NOT_FOUND = "Photo owner not found";
    public static final String PROFILE_PHOTO_ALREADY_EXISTS = "Photo for profile already exists";
}
