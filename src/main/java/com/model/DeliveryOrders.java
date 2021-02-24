package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.handler.DeliveryOrdersDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryOrders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DeliveryOrderId")
  private Long deliveryOrderId;

  @Column(name = "BaseRef", length = 64)
  private String baseRef;

  @Column(name = "NumberOrderCustomer", length = 64)
  private String numberOrderCustomer;

  //Close, open, cancelled
  @Column(name = "DocStatus", length = 1, nullable = false)
  private char docStatus;

  @ManyToOne
  @JoinColumn(name = "UserSupplierId")
  @JsonManagedReference
  private Users supplier;

  @ManyToOne
  @JoinColumn(name = "UserCustomerId")
  @JsonManagedReference
  private Users customer;

  @Column(name = "DocTotal")
  private double docTotal;

  @Column(name = "DocNet")
  private double docNet;

  @Column(name = "DocVatSum")
  private double docVatSum;

  @Column(name = "Description")
  private String description;

  @Column(name = "CreationDate", nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "ModifyDate", nullable = false)
  private Timestamp modifyDate = new Timestamp(System.currentTimeMillis());

  @OneToMany(mappedBy = "deliveryOrder", orphanRemoval = true)
  @JsonManagedReference
  private List<DetailsDeliveryOrders> detailsDeliveryOrdersList;

  @OneToMany(mappedBy = "deliveryOrders", orphanRemoval = true)
  @JsonBackReference
  private List<Subscriptions> subscriptions;

  @OneToMany(mappedBy = "deliveryOrders", orphanRemoval = true)
  @JsonBackReference
  private List<Notifications> notifications;

  private String type;

  public DeliveryOrders(DeliveryOrdersDTO deliveryOrdersDTO, Users customer, Users supplier) {
    List<DetailsDeliveryOrders> list = new ArrayList<>();
    for (DeliveryOrdersDTO.DetailsDeliveryOrdersList deliveryOrdersList : deliveryOrdersDTO.getDetailsDeliveryOrdersList()
    ) {
      list.add(DetailsDeliveryOrders.toDetailsDeliveryOrders(deliveryOrdersList));
    }
    this.baseRef = deliveryOrdersDTO.getBaseRef();
    this.numberOrderCustomer = deliveryOrdersDTO.getNumberOrderCustomer();
    this.docStatus = 'O';
    this.customer = customer;
    this.supplier = supplier;
    this.docTotal = deliveryOrdersDTO.getDocTotal();
    this.docNet = deliveryOrdersDTO.getDocNet();
    this.docVatSum = deliveryOrdersDTO.getDocVatSum();
    this.description = deliveryOrdersDTO.getDescription();
    this.detailsDeliveryOrdersList = list;
  }

  public void updateDeliveryOrders(DeliveryOrdersDTO deliveryOrdersDTO, Users customer, Users supplier) {
    this.baseRef = deliveryOrdersDTO.getBaseRef();
    this.numberOrderCustomer = deliveryOrdersDTO.getNumberOrderCustomer();
    this.docStatus = deliveryOrdersDTO.getDocStatus();
    this.customer = customer;
    this.supplier = supplier;
    this.docTotal = deliveryOrdersDTO.getDocTotal();
    this.docNet = deliveryOrdersDTO.getDocNet();
    this.docVatSum = deliveryOrdersDTO.getDocVatSum();
    this.description = deliveryOrdersDTO.getDescription();
    this.modifyDate = new Timestamp(System.currentTimeMillis());
  }
}
