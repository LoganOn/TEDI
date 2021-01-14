package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadUsernameOrPasswordException extends Exception {

  public BadUsernameOrPasswordException(String message) {
    super(message);
  }
}