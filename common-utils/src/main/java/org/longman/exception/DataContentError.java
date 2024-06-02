package org.longman.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataContentError extends RuntimeException{

    private final Integer status = HttpStatus.BAD_REQUEST.value();
    private final String message;

    public DataContentError(String message) {
        this.message = message;
    }
}
