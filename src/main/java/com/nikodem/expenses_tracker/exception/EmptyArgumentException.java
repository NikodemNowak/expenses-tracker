package com.nikodem.expenses_tracker.exception;

public class EmptyArgumentException extends RuntimeException{
    public EmptyArgumentException(String message) {
        super(message);
    }
}
