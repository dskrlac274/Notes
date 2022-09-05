package com.example.pywo.exception;

public class IdDoesNotExists extends RuntimeException {
    public IdDoesNotExists(String errorMessage) {
        super(errorMessage);
    }
}
