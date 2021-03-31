package com.bookstore.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

  private int status;
  private List<String> errorMessages;
  private long timestamp;
}