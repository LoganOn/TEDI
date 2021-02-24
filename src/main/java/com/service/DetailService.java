package com.service;

import com.google.common.eventbus.EventBus;
import com.handler.BasicDetailsDeliveryOrderDTO;
import com.handler.DeliveryOrdersDTO;
import com.handler.DetailsDeliveryOrderDTO;
import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import com.model.Users;
import com.repository.DetailsDeliveryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import javax.swing.plaf.basic.BasicArrowButton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class DetailService {

  private final EventBus eventBus;

  private final DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;


  public DetailsDeliveryOrders save(DeliveryOrders deliveryOrder, BasicDetailsDeliveryOrderDTO basicDetailsDeliveryOrderDTO) {
    DetailsDeliveryOrders detailsDeliveryOrders = DetailsDeliveryOrders.toDetailsDeliveryOrders(basicDetailsDeliveryOrderDTO);
    detailsDeliveryOrders.setParametrs(deliveryOrder);
    detailsDeliveryOrders.setType("post");
    eventBus.post(detailsDeliveryOrders);
    return detailsDeliveryOrderRepository.save(detailsDeliveryOrders);
  }

  public DetailsDeliveryOrders saveDetails(DetailsDeliveryOrders detailsDeliveryOrders) {
    detailsDeliveryOrders.setType("patch");
    eventBus.post(detailsDeliveryOrders);
    return detailsDeliveryOrderRepository.save(detailsDeliveryOrders);
  }

  public DetailsDeliveryOrders updateAll(DeliveryOrders deliveryOrders, DetailsDeliveryOrders detailsDeliveryOrders, DeliveryOrdersDTO.DetailsDeliveryOrdersList detailsDeliveryOrderDTO, Users customer, Users supplier){
    detailsDeliveryOrders.updateDetailsDeliveryOrders(deliveryOrders, detailsDeliveryOrderDTO, customer, supplier);
    return detailsDeliveryOrderRepository.save(detailsDeliveryOrders);
  }

  public DetailsDeliveryOrders update(DeliveryOrders deliveryOrders, DeliveryOrdersDTO.DetailsDeliveryOrdersList detailsDeliveryOrders, Users customer, Users supplier){
    Optional<DetailsDeliveryOrders> optionalDetailsDeliveryOrders = detailsDeliveryOrderRepository.findById(detailsDeliveryOrders.getDetailsId());
    optionalDetailsDeliveryOrders.get().updateDetailsDeliveryOrders(deliveryOrders, detailsDeliveryOrders, customer, supplier);
    optionalDetailsDeliveryOrders.get().setType("put");
    eventBus.post(optionalDetailsDeliveryOrders.get());
    return detailsDeliveryOrderRepository.save(optionalDetailsDeliveryOrders.get());
  }

  public void delete(DetailsDeliveryOrders detailsDeliveryOrders) {
    detailsDeliveryOrders.setType("delete");
    eventBus.post(detailsDeliveryOrders);
    detailsDeliveryOrderRepository.delete(detailsDeliveryOrders);
  }
}
