package com.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotRegisteredException extends Exception{

  public UserNotRegisteredException(String message) {
    super(message);
  }
}
