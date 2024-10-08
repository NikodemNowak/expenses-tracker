package com.nikodem.expenses_tracker.exception;

public class EmailTakenException extends RuntimeException {
  public EmailTakenException(String message) {
    super(message);
  }
}
