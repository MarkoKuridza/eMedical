package org.emedical.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ExceptionApi extends RuntimeException {
    private final HttpStatus status;

    protected ExceptionApi(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
