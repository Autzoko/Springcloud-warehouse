package org.longman.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MissingFieldException extends RuntimeException{

    private final Integer status = HttpStatus.NO_CONTENT.value();
    private final String message;

    public MissingFieldException(String field) {
        this.message = String.format("Missing field: '%s'.", field);
    }
}
