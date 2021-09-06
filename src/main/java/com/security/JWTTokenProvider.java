package com.Security;


import com.exception.JwtAuthenticationFailedException;
import com.provider.SecretKeyProvider;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Slf4j
@Component
@RequiredArgsConstructor
@Setter
public class JWTTokenProvider {

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final String BEARER_PREFIX = "Bearer ";

  private static final String CLAIMS_ROLES_KEY = "roles";

  private static final String CLAIMS_REFRESH_KEY = "refresh_key";

  private static final String REFRESH_VALUE = "refresh_value";

  public static final int BEARER_PREFIX_LENGTH = 7;

  private final SecretKeyProvider secretKeyProvider;

  private final UserDetailsService userDetailsService;

  private int expirationLength;

  private int expirationLengthRefresh;

  private String SECRET_FOR_MACHINE_ACCESS;

  @Value("${server.deploy}")
  private boolean deploy;

  private byte[] secretKey;


  @PostConstruct
  protected void init() throws IOException {
    secretKey = secretKeyProvider.getKey();
  }

  public String createToken(String email, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(email);
    claims.put(CLAIMS_ROLES_KEY, roles);
    Date now = new Date();
    Date due = new Date(now.getTime() + expirationLength * 1000);
    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(due)
            .signWith(HS512, secretKey)
            .compact();
  }

  public String createRefresh(String email, List<String> roles){
    Claims claims = Jwts.claims().setSubject(email);
    claims.put(CLAIMS_ROLES_KEY, roles);
    claims.put(CLAIMS_REFRESH_KEY, REFRESH_VALUE);
    Date now = new Date();
    Date due = new Date(now.getTime() + expirationLengthRefresh * 1000);
    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(due)
            .signWith(HS512, secretKey)
            .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getEmail(token));

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getEmail(String token) {
    return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    String authHeader = request.getHeader(AUTHORIZATION_HEADER);

    if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
      return authHeader.substring(BEARER_PREFIX_LENGTH);
    }
    return null;
  }

  public Map<String, Cookie> generateCookies(String accessToken, String refreshToken) {
    Cookie accessCookie = new Cookie("Token", accessToken);
    accessCookie.setMaxAge(expirationLength);
    if (deploy) accessCookie.setSecure(true);
    accessCookie.setHttpOnly(true);
    accessCookie.setPath("/");
    Cookie refreshCookie = new Cookie("RefreshToken", refreshToken);
    refreshCookie.setMaxAge(-1);
    if (deploy) refreshCookie.setSecure(true);
    refreshCookie.setHttpOnly(true);
    refreshCookie.setPath("/");
    return Map.of("accessCookie", accessCookie, "refreshCookie", refreshCookie);
  }

  public Boolean validateToken(String token) throws JwtAuthenticationFailedException {
    try {
      return Jwts.parser()
              .setSigningKey(secretKey)
              .parseClaimsJws(token)
              .getBody()
              .containsValue(SECRET_FOR_MACHINE_ACCESS)
              ||
              (Jwts.parser()
                      .setSigningKey(secretKey)
                      .parseClaimsJws(token)
                      .getBody()
                      .getExpiration()
                      .after(new Date()) && !isRefresh(token));
    } catch (JwtException | IllegalArgumentException e) {
      log.debug("token " + token + " invalid");
      return false;
    }
  }

  public Boolean isRefresh(String token){
    try{
      return Jwts.parser()
              .setSigningKey(secretKey)
              .parseClaimsJws(token)
              .getBody()
              .getExpiration()
              .after(new Date()) &&
              Jwts.parser()
                      .setSigningKey(secretKey)
                      .parseClaimsJws(token)
                      .getBody().containsKey(CLAIMS_REFRESH_KEY);
    }catch (JwtException e){
      return false;
    }
  }

  public Date getExpireDate(String token){
    return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
  }

//  public String createTokenForMachineUser(Users machineUser) {
//    Claims claims = Jwts.claims().setSubject(machineUser.getEmail());
//    claims.put(CLAIMS_ROLES_KEY, "ROLE_" + machineUser.getRole());
//    claims.put(SECRET_FOR_MACHINE_KEY, SECRET_FOR_MACHINE_ACCESS);
//    Date now = new Date();
//    return Jwts.builder()
//            .setClaims(claims)
//            .setIssuedAt(now)
//            .signWith(HS512, secretKey)
//            .compact();
//  }

  public void clearSession(HttpServletRequest request, HttpServletResponse response)  {
    SecurityContextHolder.clearContext();
    HttpSession session = request.getSession(false);
    if(session != null) {
      session.invalidate();
    }
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for(Cookie cookie : request.getCookies()) {
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
      }
    }
  }

}
