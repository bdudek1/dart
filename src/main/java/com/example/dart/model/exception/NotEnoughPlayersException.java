package com.example.dart.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Not enough players. At least 2 players required.")
public class NotEnoughPlayersException extends RuntimeException {
    public NotEnoughPlayersException() {
        super();
    }

    public NotEnoughPlayersException(String message) {
        super(message);
    }
}
