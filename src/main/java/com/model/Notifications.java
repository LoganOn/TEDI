package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

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

  @ManyToOne
  @JoinColumn(name = "UserCustomerId")
  @JsonManagedReference
  private Users customer;

  @Column(name = "Content")
  private String content;

  @Column(name = "Readed")
  private boolean readed;

  @Column(name = "CreationDate")
  private Timestamp creationDate;

  public Notifications(DeliveryOrders deliveryOrders, DetailsDeliveryOrders detailsDeliveryOrders, Users customer, String content, boolean readed, Timestamp creationDate) {
    this.deliveryOrders = deliveryOrders;
    this.detailsDeliveryOrders = detailsDeliveryOrders;
    this.customer = customer;
    this.content = content;
    this.readed = readed;
    this.creationDate = creationDate;
  }
}
