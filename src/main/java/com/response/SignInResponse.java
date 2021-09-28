package com.response;

import com.model.Users;
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

  private final String token;

  private final String refreshToken;

  public SignInResponse(Users user, String token, String refreshToken ) {
    this.userId = user.getUserId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.phone = user.getPhone();
    this.role = user.getRole();
    this.imageUrl = user.getImageUrl();
    this.userVerified = user.getUserVerified();
    this.token = token;
    this.refreshToken = refreshToken;
  }
}
