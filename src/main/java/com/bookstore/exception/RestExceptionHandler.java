package com.bookstore.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler
  public final ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
    List<String> errorMessages = new ArrayList<>();
    errorMessages.add(e.getMessage());

    return exceptionResponseCreator(HttpStatus.NOT_FOUND, errorMessages);
  }

  @ExceptionHandler
  public final ResponseEntity<ExceptionResponse> handleInsufficientStockException(InsufficientStockException e) {
    List<String> errorMessages = new ArrayList<>();
    errorMessages.add(e.getMessage());

    return exceptionResponseCreator(HttpStatus.BAD_REQUEST, errorMessages);
  }

  private final ResponseEntity<ExceptionResponse> exceptionResponseCreator(HttpStatus status, List<String> errorMessages) {
    ExceptionResponse response = new ExceptionResponse(status.value(),
        errorMessages,
        System.currentTimeMillis());
    return new ResponseEntity<>(response, status);
  }
}