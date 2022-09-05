package com.example.pywo.exception;

public class InvalidCredentials extends RuntimeException{
    public InvalidCredentials(String errorMessage) {
        super(errorMessage);
    }
}
