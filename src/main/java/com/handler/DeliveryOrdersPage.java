package com.handler;

import com.model.DeliveryOrders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryOrdersPage{

  private long count;

  private List<DeliveryOrders> deliveryOrders;
}
