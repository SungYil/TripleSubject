package com.triple.TripleSubject.handlers;

import com.triple.TripleSubject.dtos.ErrorResponse;
import com.triple.TripleSubject.exceptions.CheckedException;
import com.triple.TripleSubject.exceptions.DuplicatedException;
import com.triple.TripleSubject.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CheckedException.class)
    public ResponseEntity<ErrorResponse> handleCheckedException(CheckedException exception){
        log.error(exception.getMessage(),exception);
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception){
        log.warn(exception.getMessage(),exception);
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedException(DuplicatedException exception){
        log.warn(exception.getMessage());
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
