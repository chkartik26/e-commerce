package com.example.e_commerce.exception;

public class AccessDenied extends RuntimeException {
    public AccessDenied(String message) {
        super(message);
    }
}
