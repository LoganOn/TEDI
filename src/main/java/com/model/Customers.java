package com.model;

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
public class Customers {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "CustomerId")
  private Long customerId;

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

  @OneToMany(mappedBy = "customer", orphanRemoval = true)
  @JsonManagedReference
  private List<DeliveryOrders> deliveryOrdersList;

  @OneToMany(mappedBy = "customer", orphanRemoval = true)
  @JsonManagedReference
  private List<Relations> relationsList;
}
