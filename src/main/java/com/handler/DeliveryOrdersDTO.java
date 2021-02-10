package com.handler;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

  private UserSDTO supplier;

  private UserSDTO customer;

  private double docTotal;

  private double docNet;

  private double docVatSum;

  private String description;

  private List<DetailsDeliveryOrdersList> detailsDeliveryOrdersList;

  @Getter
  @Setter
  @ToString
  static class UserSDTO {
    long id;
  }

  @Getter
  @Setter
  @ToString
  static class DetailsDeliveryOrdersList {
    String itemCode;
    String itemName;
    Double quantity;
    String codeBars;
    double price;
    String currency;
    double valueTotal;
    double valueNet;
    double valueVat;
    String discountPercent;
    String vatPercent;
    String scheduledShipDate;
  }
}






