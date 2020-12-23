package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Suppliers {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "SupplierId")
  private Long supplierId;

  @Column(name = "Role", length = 20)
  private String role;

  @Column(name = "Name")
  private String name;

  @Column(name = "Phone", length = 20)
  private String phone;

  @Column(name = "Email", nullable = false, unique = true, length = 64)
  private String email;

  @Column(name = "Password", nullable = false, length = 64)
  private String password;

  @Column(name = "ImageUrl")
  private String imageUrl;

  @Column(name = "Recipient")
  private String recipient;

  @Column(name = "RecipientId")
  private Long recipientId;

  @Column(name = "CreationDate",  nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "UserVerified")
  private Boolean userVerified;

  @Column(name = "Active")
  private Boolean active;
}
