package org.longman.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IdConflictException extends RuntimeException{

    private final Integer status = HttpStatus.CONFLICT.value();

    private final String message;

    public IdConflictException(String message) {
        this.message = message;
    }
}
