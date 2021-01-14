package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ValidationFailure extends Exception{

  public ValidationFailure(String msg, Throwable t) {
    super(msg, t);
  }

  public ValidationFailure(String msg) {
    super(msg);
  }
}
