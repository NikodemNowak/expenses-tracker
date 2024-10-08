package com.nikodem.expenses_tracker.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Data
public class ApiError {
    private List<String> errors;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

    public ApiError(List<String> errors, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.errors = errors;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public ApiError(String error, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.errors = Collections.singletonList(error);
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
