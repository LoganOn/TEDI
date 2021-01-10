package com.service;

import com.model.Customers;
import com.model.UserPrincipal;
import com.repository.CustomersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final CustomersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Customers> users = usersRepository.findByEmail(email);

    if(users != null) return UserPrincipal.create(users.get());
    else throw new UsernameNotFoundException("User not found with email: " + email);
  }
}
