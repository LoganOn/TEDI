package com.security.JWT;


import com.exception.JwtAuthenticationFailedException;
import com.model.JWTBlackList;
import com.service.JWTBlackListService;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;


@RequiredArgsConstructor
@Slf4j
public class JWTTokenFilter extends GenericFilterBean {

  private final String AUTHENTICATION_FAILED_MESS = "Authentication failed -- Filter (Invalid Token)";

  private final String TOKEN_IS_NULL = "Token is null";

  private final String TOKEN_EMPTY = "Token is empty";

  private final String TOKEN_NOT_VALID = "Token is not valid";

  private final JWTTokenProvider jwtTokenProvider;

  private final JWTBlackListService jwtBlackListService;

  @Override
  public void doFilter(
          ServletRequest request,
          ServletResponse response,
          FilterChain chain
  ) throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
      try {
        if (token != null && jwtTokenProvider.validateToken(token) && !jwtBlackListService.existsByToken(token)) {
          Authentication authentication = jwtTokenProvider.getAuthentication(token);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
          if(token != null && token.isEmpty())
            log.info(TOKEN_EMPTY);
          else if(token == null)
            log.info(TOKEN_IS_NULL);
          else {
            JWTBlackList jwtBlackList = new JWTBlackList(token,1L);
            jwtBlackListService.save(jwtBlackList);
            log.info(TOKEN_NOT_VALID);
          }
        }
      } catch (JwtAuthenticationFailedException ex) {
        log.debug(AUTHENTICATION_FAILED_MESS + token);
      }
    }
    chain.doFilter(request, response);
  }
}
