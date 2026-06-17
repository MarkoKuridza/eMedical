package org.emedical.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ExceptionApi {
    public NotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
