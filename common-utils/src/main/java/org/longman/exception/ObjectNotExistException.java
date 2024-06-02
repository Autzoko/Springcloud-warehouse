package org.longman.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ObjectNotExistException extends RuntimeException{

    private final Integer status = HttpStatus.BAD_REQUEST.value();
    private final String message;

    public ObjectNotExistException(String message) {
        this.message = message;
    }
}
