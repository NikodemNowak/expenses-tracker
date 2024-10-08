package com.nikodem.expenses_tracker.exception;

public class ExpenseInFutureException extends RuntimeException {
    public ExpenseInFutureException(String message) {
        super(message);
    }
}
