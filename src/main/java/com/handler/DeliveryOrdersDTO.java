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

  private UsersDTO supplier;

  private UsersDTO customer;

  private double docTotal;

  private double docNet;

  private double docVatSum;

  private String description;

  private List<DetailsDeliveryOrdersList> detailsDeliveryOrdersList;

  @Getter
  public static class UsersDTO {
    long userId;
  }

  @Getter
  public static class DetailsDeliveryOrdersList extends BasicDetailsDeliveryOrderDTO {

    private Boolean active;

    private Boolean onTheWay;
  }
}






