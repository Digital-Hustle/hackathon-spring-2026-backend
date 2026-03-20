package ru.core.profilems.exception.exception;

import ru.digital_hustle.exceptions_starter.exception.DomainException;

public class ProfilePhotoAlreadyExists extends DomainException {
    public ProfilePhotoAlreadyExists(String message) {
        super(message);
    }
}
