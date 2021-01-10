package com.controller;

import com.Exception.BadUsernameOrPasswordException;
import com.Exception.UserNotFoundException;
import com.Exception.UserNotRegisteredException;
import com.Exception.UserNotVerifiedException;
import com.Response.SignInResponse;
import com.model.Customers;
import com.model.LoginCredentials;
import com.model.UserPrincipal;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
@Slf4j
@RequiredArgsConstructor
public class SignInController {

  private final String WRONG_USERNAME_PASSWORD = "Invalid username or password";

  private final String WRONG_INSTANCE = "UserDetails isn't UserPrincipal class";

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final UserService userService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public SignInResponse signIn(@RequestBody LoginCredentials loginCredentials, HttpServletResponse response)
          throws UserNotFoundException, BadUsernameOrPasswordException, UserNotRegisteredException {
    String email = loginCredentials.getEmail();
    String password = loginCredentials.getPassword();
    try {
      Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
      authenticationManager.authenticate(authentication);
    } catch (BadCredentialsException e) {
      throw new BadUsernameOrPasswordException(WRONG_USERNAME_PASSWORD);
    }
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    if (userDetails instanceof UserPrincipal) {
      Optional<Customers> users_info = userService.loadUserByEmail(userDetails.getUsername());
      if (users_info.isPresent()) {
        if (users_info.get().getUserVerified()) {
          return new SignInResponse(users_info.get());
        } else {
          throw new UserNotVerifiedException("User " + userDetails.getUsername() + " not verified");
        }
      } else {
        throw new UserNotFoundException("User with email " + userDetails.getUsername() + " not found");
      }
    } else {
      throw new UserNotRegisteredException(WRONG_INSTANCE);
    }
  }
}
