package com.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DetailsDeliveryOrderDTO{

  private int deliverOrderID;

  private List <BasicDetailsDeliveryOrderDTO> detailsDeliveryOrders;
}
