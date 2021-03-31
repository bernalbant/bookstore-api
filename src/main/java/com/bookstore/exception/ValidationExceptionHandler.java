package com.bookstore.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

  @ExceptionHandler
  public final ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
    List<String> errorMessages = new ArrayList<>();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      errorMessages.add(fieldError.getDefaultMessage());
    }

    return exceptionResponseCreator(HttpStatus.BAD_REQUEST, errorMessages);
  }

  private final ResponseEntity<ExceptionResponse> exceptionResponseCreator(HttpStatus status, List<String> errorMessages) {
    ExceptionResponse response = new ExceptionResponse(status.value(),
        errorMessages,
        System.currentTimeMillis());
    return new ResponseEntity<>(response, status);
  }
}