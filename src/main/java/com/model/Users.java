package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "UserId")
  private Long userId;

  @Column(name = "Role", length = 20)
  private String role;

  @Column(name = "Name")
  private String name;

  @Column(name = "Phone", length = 20)
  private String phone;

  @Column(name = "Email", nullable = false, unique = true, length = 64)
  private String email;

  @Column(name = "Password", nullable = false, length = 255)
  private String password;

  @Column(name = "ImageUrl")
  private String imageUrl;

  @Column(name = "Provider")
  private String provider;

  @Column(name = "ProviderId")
  private Long providerId;

  @Column(name = "CreationDate",  nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "UserVerified")
  private Boolean userVerified;

  @Column(name = "Active")
  private Boolean active;

  @Column(name = "Notification")
  private Boolean notification;

  public Users(String name, String email, String password, String phone, String role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.role = role;
  }

  @OneToMany(mappedBy = "customer", orphanRemoval = true)
  @JsonBackReference
  private List<DeliveryOrders> deliveryOrdersList;

  @OneToMany(mappedBy = "supplier", orphanRemoval = true)
  @JsonBackReference
  private List<DeliveryOrders> deliveryOrdersList2;

  @OneToMany(mappedBy = "supplier", orphanRemoval = true)
  @JsonBackReference
  private List<RelationsUsers> relationsList1;

  @OneToMany(mappedBy = "customer", orphanRemoval = true)
  @JsonBackReference
  private List<RelationsUsers> relationsList2;

  @OneToMany(mappedBy = "customer", orphanRemoval = true)
  @JsonBackReference
  private List<Subscriptions> subscriptions;
}
