package com.example.dart.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid shot. Please select score between 0 and 20.")
public class InvalidShotException extends RuntimeException {
    public InvalidShotException() {
        super();
    }

    public InvalidShotException(String message) {
        super(message);
    }
}
