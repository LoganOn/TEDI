package com.security;

import com.exception.JwtAuthenticationFailedException;
import com.service.JWTBlackListService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component("jwtFilter")
@RequiredArgsConstructor
@Slf4j

public class JwtTokenFilter extends OncePerRequestFilter {

  private final String AUTHENTICATION_FAILED_MESS = "Authentication failed -- Filter (Invalid Token)";

  private final com.Security.JWTTokenProvider jwtTokenProvider;

  private final JWTBlackListService jwtBlackListService;

  private final UserDetailsService userDetailsService;

  @Value("${security.jwt.token.expiration-length}")
  private int expirationLength;

  @SneakyThrows
  @Override
  public void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain chain
  )throws ResourceAccessException, SignatureException {
    final Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      final Cookie token = Arrays.stream(cookies).filter(
              cookie -> "Token".equals(cookie.getName())
      ).findFirst().orElse(null);       if (token != null && token.getValue().split("\\.").length == 3 && jwtTokenProvider.validateToken(token.getValue()) && !jwtBlackListService.existsByToken(token.getValue())) {
        try {
          Authentication authentication = jwtTokenProvider.getAuthentication(token.getValue());
          SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException ex) {
          tryToRefresh(request, response);
          log.debug(AUTHENTICATION_FAILED_MESS + token);
        }
      }
      chain.doFilter(request, response);
    }
  }

  private void tryToRefresh(HttpServletRequest request, HttpServletResponse response)
          throws ResourceAccessException, SignatureException {
    try {
      refreshToken(request, response);
    } catch (SignatureException | MalformedJwtException e) {
      throw new SignatureException("UNTRUSTED_REFRESH_TOKEN");
    }
  }

  private void refreshToken(HttpServletRequest request,
                            HttpServletResponse response)
          throws SignatureException, MalformedJwtException, ResourceAccessException {
    String email;
    final Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      final Cookie refreshToken = Arrays.stream(cookies).filter(
              cookie -> "RefreshToken".equals(cookie.getName())
      ).findFirst().orElse(null);
      if (refreshToken != null) {
        try {
          email = jwtTokenProvider.getEmail(refreshToken.getValue());
          UserDetails userDetails = userDetailsService.loadUserByUsername(email);
          ArrayList<String> userRoles = (ArrayList<String>) userDetails.getAuthorities()
                  .stream()
                  .map(GrantedAuthority::getAuthority)
                  .collect(Collectors.toList());
          if (jwtTokenProvider.validateToken(refreshToken.getValue())) {
            final String newToken = jwtTokenProvider.createToken(email, userRoles);
            final Cookie tokenCookie = new Cookie("Token", newToken);
            tokenCookie.setMaxAge(expirationLength);
            tokenCookie.setPath("/");
            tokenCookie.setHttpOnly(true);
//            if (deploy) tokenCookie.setSecure(true);
            response.addCookie(tokenCookie);
            log.warn("New token has been created");
          }
        } catch (ExpiredJwtException | JwtAuthenticationFailedException ex) {
          jwtTokenProvider.clearSession(request, response);
        }
      }
    }
  }
}

