package com.example.Account_Service;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}

