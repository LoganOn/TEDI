package com.service;

import com.google.common.eventbus.EventBus;
import com.handler.DeliveryOrdersDTO;
import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import com.model.Users;
import com.repository.DeliveryOrdersRepository;
import com.repository.DetailsDeliveryOrderRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderService {

  private final EventBus eventBus;

  private final DeliveryOrdersRepository deliveryOrdersRepository;

  private final DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;

  private final DetailService detailService;


  public DeliveryOrders save(DeliveryOrdersDTO deliveryOrdersDTO, Users customer, Users supplier) {
    DeliveryOrders deliveryOrders = new DeliveryOrders(deliveryOrdersDTO, customer, supplier);
    deliveryOrdersRepository.save(deliveryOrders);
    deliveryOrders.getDetailsDeliveryOrdersList().forEach(x -> {
      x.setParametrs(deliveryOrders);
      detailsDeliveryOrderRepository.save(x);
    });
    deliveryOrders.setType("post");
    eventBus.post(deliveryOrders);
    return deliveryOrders;
  }

  public DeliveryOrders saveDeliveryOrders(DeliveryOrders deliveryOrders) {
    deliveryOrders.setType("patch");
    eventBus.post(deliveryOrders);
    return deliveryOrdersRepository.save(deliveryOrders);
  }

  public DeliveryOrders update(Optional<DeliveryOrders> optionalDeliveryOrders, DeliveryOrdersDTO deliveryOrdersDTO, Users customer, Users supplier){
    DeliveryOrders deliveryOrders = optionalDeliveryOrders.get();
    deliveryOrders.updateDeliveryOrders(deliveryOrdersDTO, customer, supplier);
    deliveryOrders.setType("put");
    deliveryOrdersRepository.save(deliveryOrders);
    if( deliveryOrdersDTO.getDetailsDeliveryOrdersList() != null && !deliveryOrdersDTO.getDetailsDeliveryOrdersList().isEmpty()) {
      deliveryOrdersDTO.getDetailsDeliveryOrdersList().forEach(x -> {
        Optional<DetailsDeliveryOrders> detailsDeliveryOrders = detailsDeliveryOrderRepository.findById(x.getDetailsId());
        if (detailsDeliveryOrders.isPresent())
          detailService.updateAll(deliveryOrders, detailsDeliveryOrders.get(), x, customer, supplier);
      });
    }
    eventBus.post(deliveryOrders);
    return deliveryOrders;
  }

  public void delete(DeliveryOrders deliveryOrders) {
    List<DetailsDeliveryOrders> detailsDeliveryOrdersList = detailsDeliveryOrderRepository.findAllByDeliveryOrder(deliveryOrders);
    if (!detailsDeliveryOrdersList.isEmpty()) {
      for (DetailsDeliveryOrders d : detailsDeliveryOrdersList
      ) {
        detailService.delete(d);
      }
    }
    deliveryOrders.setType("delete");
    eventBus.post(deliveryOrders);
    deliveryOrdersRepository.delete(deliveryOrders);
  }
}
