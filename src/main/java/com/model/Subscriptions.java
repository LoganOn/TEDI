package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Subscriptions {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SubscriptionsId")
  private Long subscriptionsId;

  @ManyToOne
  @JoinColumn(name = "DeliveryOrderId")
  @JsonManagedReference
  private DeliveryOrders deliveryOrders;

  @ManyToOne
  @JoinColumn(name = "DetailDeliveryOrderId")
  @JsonManagedReference
  private DetailsDeliveryOrders detailsDeliveryOrders;

  @ManyToOne
  @JoinColumn(name = "UserCustomerId")
  @JsonManagedReference
  private Users customer;

  private Boolean email;

  @Column(name = "CreationDate")
  private Timestamp creationDate;
}
