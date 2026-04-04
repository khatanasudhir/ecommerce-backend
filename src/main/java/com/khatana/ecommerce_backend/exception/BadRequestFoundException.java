package com.khatana.ecommerce_backend.exception;

public class BadRequestFoundException extends RuntimeException{

    public BadRequestFoundException(String message) {
        super(message);
    }
}
