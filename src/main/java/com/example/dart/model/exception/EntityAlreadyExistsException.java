package com.example.dart.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Resource already exists.")
public class EntityAlreadyExistsException extends RuntimeException
{
    public EntityAlreadyExistsException(String message)
    {
        super(message);
    }

    public EntityAlreadyExistsException() { }
}
