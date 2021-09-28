package com.security.JWT;

import com.service.JWTBlackListService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JWTTokenProvider jwtTokenProvider;

  private final JWTBlackListService jwtBlackListService;

  @Override
  public void configure(HttpSecurity builder) {
    builder.addFilterBefore(
            new JWTTokenFilter(jwtTokenProvider, jwtBlackListService),
            UsernamePasswordAuthenticationFilter.class
    );
  }
}
