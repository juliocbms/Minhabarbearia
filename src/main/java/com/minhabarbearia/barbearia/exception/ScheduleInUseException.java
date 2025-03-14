package com.minhabarbearia.barbearia.exception;

public class ScheduleInUseException extends RuntimeException{
    public ScheduleInUseException(String message) {
        super(message);
    }
}
