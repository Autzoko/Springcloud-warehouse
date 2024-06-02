package org.longman.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JsonDataError extends RuntimeException{

    private final Integer status = HttpStatus.NO_CONTENT.value();
    private final String message;

    public JsonDataError(String message) {
        this.message = message;
    }
}
