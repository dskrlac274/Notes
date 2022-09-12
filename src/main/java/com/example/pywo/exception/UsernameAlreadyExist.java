package com.example.pywo.exception;

public class UsernameAlreadyExist extends RuntimeException{

    public UsernameAlreadyExist(String errorMessage) {
        super(errorMessage);
    }

}
