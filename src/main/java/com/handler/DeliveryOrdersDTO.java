package com.handler;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.DetailsDeliveryOrders;
import com.model.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class DeliveryOrdersDTO {

  private Long deliveryOrderId;

  private String baseRef;

  private String numberOrderCustomer;

  private char DocStatus;

  private Users supplier;

  private Users customer;

  private double docTotal;

  private double docNet;

  private double docVatSum;

  private String description;

  private Timestamp creationDate;

  private Timestamp modifyDate;
  @JsonIgnore
  private List<DetailsDeliveryOrders> detailsDeliveryOrdersList;
}



