package com.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
public class DeliveryOrdersDTO {

  private Long deliveryOrderId;

  private String baseRef;

  private String numberOrderCustomer;

  private char DocStatus;

  private String supplier;

  private String customer;

  private double docTotal;

  private double docNet;

  private double docVatSum;

  private String description;

  private Timestamp creationDate;

  private Timestamp modifyDate;
}



