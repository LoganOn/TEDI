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
public class DeliveryOrders {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "DeliveryOrderId")
  private Long deliveryOrderId;

  @Column(name = "BaseRef", length = 64)
  private String baseRef;

  @Column(name = "NumberOrderCustomer", length = 64)
  private String numberOrderCustomer;

  @Column(name = "DocNumberPositions")
  private int DocNumberPositions;

  //Close, open, cancelled
  @Column(name = "DocStatus", length = 1, nullable = false)
  private char DocStatus;

  @ManyToOne
  @Column(name = "SupplierId")
  private Suppliers suppliers;

  @ManyToOne
  @Column(name = "CustomerId")
  private Customers customers;

  @Column(name = "DocTotal")
  private double docTotal;

  @Column(name = "DocNet")
  private double docNet;

  @Column(name = "DocVatSum")
  private double docVatSum;

  @Column(name = "Description")
  private String description;

  @Column(name = "CreationDate",  nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "ModifyDate",  nullable = false)
  private Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
}
