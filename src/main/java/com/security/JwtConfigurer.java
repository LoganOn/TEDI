package com.security;

import com.service.JWTBlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final com.Security.JWTTokenProvider jwtTokenProvider;

  private final JWTBlackListService jwtBlackListService;

  private final UserDetailsService userDetailsService;

  @Override
  public void configure(HttpSecurity builder) {
    builder.addFilterBefore(
            new JwtTokenFilter(jwtTokenProvider, jwtBlackListService, userDetailsService),
            UsernamePasswordAuthenticationFilter.class
    );
  }
}

