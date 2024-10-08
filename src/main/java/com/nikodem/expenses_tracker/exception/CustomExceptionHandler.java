package com.nikodem.expenses_tracker.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(
                errors,
                badRequest,
                ZonedDateTime.now()
        );

        return handleExceptionInternal(
                ex, apiError, headers, badRequest, request
        );
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(error, badRequest, ZonedDateTime.now());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), badRequest);
    }



    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiError apiError = new ApiError(
                e.getMessage(),
                notFound,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiError, notFound);
    }

    @ExceptionHandler(value = {ExpenseInFutureException.class})
    public ResponseEntity<Object> handleExpenseInFutureException(ExpenseInFutureException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiError, badRequest);
    }

    @ExceptionHandler(value = {EmailTakenException.class})
    public ResponseEntity<Object> handleEmailTakenException(EmailTakenException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiError, badRequest);
    }
}
