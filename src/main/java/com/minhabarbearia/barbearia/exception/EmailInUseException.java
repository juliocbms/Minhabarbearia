package com.minhabarbearia.barbearia.exception;

public class EmailInUseException extends RuntimeException{
    public EmailInUseException(String message) {
        super(message);
    }
}
