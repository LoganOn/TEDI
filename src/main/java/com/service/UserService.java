package com.service;

import com.handler.UserSignupDto;
import com.model.Users;
import com.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService {

  @Lazy
  private final PasswordEncoder passwordEncoder;

  private final UsersRepository usersRepository;

  public Optional<Users> loadUserByEmail(String email){
    Optional<Users> user = usersRepository.findByEmail(email);
    return user;
  }

    public Users save(UserSignupDto signupDto){
        Users user = new Users(signupDto.getName(), signupDto.getEmail(), passwordEncoder.encode(signupDto.getPassword()),signupDto.getPhone(), "ABSTRACT_SYSTEM_USER");
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