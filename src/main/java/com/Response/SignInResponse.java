package com.Response;

import com.model.Customers;
import lombok.Data;

@Data
public class SignInResponse {

  private final Long userId;

  private final String email;

  private final String name;

  private final String phone;

  private final String role;

  private final String imageUrl;

  private final Boolean userVerified;

  public SignInResponse(Customers user) {
    this.userId = user.getCustomerId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.phone = user.getPhone();
    this.role = user.getRole();
    this.imageUrl = user.getImageUrl();
    this.userVerified = user.getUserVerified();
  }
}

