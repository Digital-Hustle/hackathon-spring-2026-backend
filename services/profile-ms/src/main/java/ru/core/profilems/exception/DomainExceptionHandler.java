package ru.core.profilems.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.core.profilems.exception.exception.AccessDeniedException;
import ru.core.profilems.exception.exception.BucketCreationException;
import ru.core.profilems.exception.exception.InvalidPhotoExtension;
import ru.core.profilems.exception.exception.PhotoProcessingException;
import ru.core.profilems.exception.exception.ProfilePhotoAlreadyExists;
import ru.digital_hustle.exceptions_starter.constant.ExceptionConstants;
import ru.digital_hustle.exceptions_starter.dto.response.ExceptionRs;
import ru.digital_hustle.exceptions_starter.factory.ExceptionResponseFactory;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class DomainExceptionHandler {

    private final ExceptionResponseFactory exceptionResponseFactory;

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleIllegalArgumentException(IllegalArgumentException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newBadRequest(exception.getMessage());
    }

    @ExceptionHandler(InvalidPhotoExtension.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleInvalidPhotoExtension(InvalidPhotoExtension exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newBadRequest(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionRs handleAccessDeniedException(AccessDeniedException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newForbidden(exception.getMessage());
    }

    @ExceptionHandler(ProfilePhotoAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionRs handleProfilePhotoAlreadyExists(ProfilePhotoAlreadyExists exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newConflict(exception.getMessage());
    }

    @ExceptionHandler(PhotoProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRs handlePhotoProcessingException(PhotoProcessingException exception) {
        log.error(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return exceptionResponseFactory.newInternalServerError();
    }

    @ExceptionHandler(BucketCreationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRs handleBucketCreationException(BucketCreationException exception) {
        log.error(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return exceptionResponseFactory.newInternalServerError();
    }
}
