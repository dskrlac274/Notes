package com.example.pywo.exception;

public class JwtExpired extends RuntimeException{
    public JwtExpired(String errorMessage) {
        super(errorMessage);
    }
}
