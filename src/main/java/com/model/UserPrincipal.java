package com.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserPrincipal implements UserDetails, Serializable {

  private Long id;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public static UserPrincipal create(Users user) {
    List<GrantedAuthority> authorities = Collections.
            singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

    return UserPrincipal.builder()
            .id(user.getUserId())
            .email(user.getEmail())
            .password(user.getPassword())
            .authorities(authorities)
            .build();
  }

  public static UserPrincipal create(Users user, Map<String, Object> attributes) {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    userPrincipal.setAttributes(attributes);
    return userPrincipal;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
