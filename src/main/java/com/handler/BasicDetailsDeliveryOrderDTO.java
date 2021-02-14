package com.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicDetailsDeliveryOrderDTO {

  private String itemCode;

  private String itemName;

  private Double quantity;

  private String codeBars;

  private double price;

  private String currency;

  private double valueTotal;

  private double valueNet;

  private double valueVat;

  private String discountPercent;

  private double vatPercent;

  private String scheduledShipDate;
}
