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
public class Notifications {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "NotificationId")
  private Long notificationId;

  @ManyToOne
  @JoinColumn(name = "DeliveryOrderId")
  @JsonManagedReference
  private DeliveryOrders deliveryOrders;

  @ManyToOne
  @JoinColumn(name = "DetailDeliveryOrderId")
  @JsonManagedReference
  private DetailsDeliveryOrders detailsDeliveryOrders;

  @Column(name = "Content")
  private String content;

  @Column(name = "Readed")
  private boolean readed;

  @Column(name = "CreationDate")
  private Timestamp creationDate;
}
