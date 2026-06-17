package org.emedical.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ExceptionApi {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
