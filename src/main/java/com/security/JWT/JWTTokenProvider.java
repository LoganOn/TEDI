package com.security.JWT;

import com.exception.JwtAuthenticationFailedException;
import com.model.Users;
import com.provider.SecretKeyProvider;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;


  @Slf4j
  @Component
  @RequiredArgsConstructor
  @Setter
  public class JWTTokenProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    private static final String CLAIMS_ROLES_KEY = "roles";

    private static final String CLAIMS_REFRESH_KEY = "refresh_key";

    private static final String SECRET_FOR_MACHINE_KEY = "secret_for_machine";

    private static final String REFRESH_VALUE = "refresh_value";

    public static final int BEARER_PREFIX_LENGTH = 7;

    private final UserDetailsService userDetailsService;

    private final SecretKeyProvider secretKeyProvider;

    @Value("${security.jwt.token.expiration-length}")
    private Long expirationLength;

    @Value("${security.jwt.token.expiration-length-refresh}")
    private Long expirationLengthRefresh;

    @Value("${secret.for.machine.access.token}")
    private String SECRET_FOR_MACHINE_ACCESS;

    private byte[] secretKey;

    @PostConstruct
    protected void init() throws IOException{
      secretKey = secretKeyProvider.getKey();
    }

    public String createToken(String email, List<String> roles) {
      Claims claims = Jwts.claims().setSubject(email);
      claims.put(CLAIMS_ROLES_KEY, roles);
      Date now = new Date();
      Date due = new Date(now.getTime() + expirationLength);
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
      Date due = new Date(now.getTime() + expirationLengthRefresh);
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

    public String createTokenForUserApi(Users userApi) {
      Claims claims = Jwts.claims().setSubject(userApi.getEmail());
      claims.put(CLAIMS_ROLES_KEY, "ROLE_" + userApi.getRole());
      claims.put(SECRET_FOR_MACHINE_KEY, SECRET_FOR_MACHINE_ACCESS);
      Date now = new Date();
      return Jwts.builder()
              .setClaims(claims)
              .setIssuedAt(now)
              .signWith(HS512, secretKey)
              .compact();
    }
  }
