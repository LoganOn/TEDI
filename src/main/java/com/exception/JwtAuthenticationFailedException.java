package com.exception;

public class JwtAuthenticationFailedException extends Exception{

  public JwtAuthenticationFailedException(String message) {
    super(message);
  }
}
