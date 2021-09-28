package com.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JWTBlackList implements Serializable {

  @Id
  @Column(name = "Token")
  private String token;

  @Column(name = "ExpireTime")
  private Long expireTime;
}
