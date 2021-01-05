package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class DetailsDeliveryOrders {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "DeliveryOrderId")
  private Long deliveryOrderId;

  @Column(name = "LineNum")
  private int lineNum;

  @Column(name = "BaseRef", length = 64)
  private String baseRef;

  @ManyToOne
  @JoinColumn(name = "SupplierId")
  @JsonBackReference
  private Suppliers suppliers;

  @ManyToOne
  @JoinColumn(name = "CustomerId")
  @JsonBackReference
  private Customers customers;

  @Column(name = "ItemCode", length = 100)
  private String itemCode;

  @Column(name = "ItemName")
  private String itemName;

  @Column(name = "Quantity")
  private double quantity;

  @Column(name = "CodeBars", length = 13)
  private String codeBars;

  @Column(name = "Price")
  private double price;

  @Column(name = "Currency", length = 10)
  private String currency;

  @Column(name = "LineTotal")
  private double lineTotal;

  @Column(name = "LineNet")
  private double lineNet;

  @Column(name = "LineVat")
  private double lineVat;

  @Column(name = "DiscountPrcnt", length = 10)
  private String discountPrcnt;

  @Column(name = "VatPrcnt")
  private double vatPrcnt;

  @Column(name = "VatGroup", length = 10)
  private String vatGroup;

  @Column(name = "Active")
  private Boolean active;

  @Column(name = "OnTheWay")
  private Boolean onTheWay;

  @Column(name = "ScheduledShipDate", length = 64)
  private String scheduledShipDate;

  @Column(name = "Comments")
  private String comments;

  @Column(name = "CreationDate",  nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "ModifyDate",  nullable = false)
  private Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
}
