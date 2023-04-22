package com.example.dart.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Cannot perform a shot in game which is not in progress.")
public class GameNotInProgressException extends RuntimeException {
    public GameNotInProgressException() {
        super();
    }
}
