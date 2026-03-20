package ru.core.profilems.exception.exception;

import ru.digital_hustle.exceptions_starter.exception.DomainException;

public class InvalidPhotoExtension extends DomainException {
    public InvalidPhotoExtension(String message) {
        super(message);
    }
}
