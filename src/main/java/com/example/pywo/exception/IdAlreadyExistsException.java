package com.example.pywo.exception;

public class IdAlreadyExistsException extends RuntimeException{
    public IdAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
