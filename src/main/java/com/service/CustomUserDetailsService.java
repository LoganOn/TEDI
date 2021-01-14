package com.service;

import com.model.UserPrincipal;
import com.model.Users;
import com.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Users> users = usersRepository.findByEmail(email);

    if(users != null) return UserPrincipal.create(users.get());
    else throw new UsernameNotFoundException("User not found with email: " + email);
  }
}