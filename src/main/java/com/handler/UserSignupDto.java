package com.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@FieldMatch.List({
        @FieldMatch(first = "password", second = "repeatPassword", message = "The password fields must match")
})
public class UserSignupDto {

  private String name;

  private String surname;

  @NotEmpty
  private String password;

  @NotEmpty
  private String repeatPassword;

  @Email(message = "Not a proper e-mail format")
  @NotEmpty
  private String email;

  private String phone;

  private String role;
}

