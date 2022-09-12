package com.example.pywo.exception;

public class IdDoesNotExist extends RuntimeException {
    public IdDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}
