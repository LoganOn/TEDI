package com.controller;

import com.exception.BadUsernameOrPasswordException;
import com.exception.UserNotFoundException;
import com.exception.UserNotRegisteredException;
import com.exception.UserNotVerifiedException;
import com.model.LoginCredentials;
import com.model.UserPrincipal;
import com.model.Users;
import com.response.SignInResponse;
import com.security.JWT.JWTTokenProvider;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
@Slf4j
@RequiredArgsConstructor
public class SignInController {

  private final String WRONG_USERNAME_PASSWORD = "Invalid username or password";

  private final String WRONG_INSTANCE = "UserDetails isn't UserPrincipal class";

  private final AuthenticationManager authenticationManager;

  private final JWTTokenProvider jwtTokenProvider;

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
    ArrayList<String> userRoles = (ArrayList<String>) userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    String token = jwtTokenProvider.createToken(email, userRoles);
    String refreshToken = jwtTokenProvider.createRefresh(email, userRoles);
    response.setHeader("Authorization", "Bearer " + token);

    if (userDetails instanceof UserPrincipal) {
      Optional<Users> users_info = userService.loadUserByEmail(userDetails.getUsername());
      if (users_info.isPresent()) {
        if (users_info.get().getUserVerified()) {
          return new SignInResponse(users_info.get(), token, refreshToken);
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