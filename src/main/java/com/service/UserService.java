package com.service;

import com.handler.UserMinimalInfo;
import com.handler.UserSignupDto;
import com.model.UserPrincipal;
import com.model.Users;
import com.repository.UsersRepository;
import com.security.JWT.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService {

  @Lazy
  private final PasswordEncoder passwordEncoder;

  private final UsersRepository usersRepository;

  private final JWTTokenProvider jwtTokenProvider;

  public Optional<Users> loadUserByEmail(String email){
    Optional<Users> user = usersRepository.findByEmail(email);
    return user;
  }

    public UserMinimalInfo getCurrentUser() {
        HttpServletRequest req = getCurrentHttpRequest();
        if (req == null) {
            return null;
        }
        String authorization = req.getHeader("authorization");
        if (authorization == null) {
            return null;
        }
        String token = authorization.substring("Bearer ".length());
        return jwtTokenProvider.getCurrentUser(token);
    }

    public static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    public Users save(UserSignupDto signupDto){
        Users user = new Users(signupDto.getName(), signupDto.getEmail(), passwordEncoder.encode(signupDto.getPassword()),signupDto.getPhone(), signupDto.getRole());
        return usersRepository.save(user);
    }

    public Users save(Users user){
        return usersRepository.save(user);
    }

    public Users update(Users user){
        return usersRepository.save(user);
    }

    public void delete(Users user){
        usersRepository.delete(user);
    }

    public boolean exists(String email){
        return usersRepository.existsByEmail(email);
    }
}