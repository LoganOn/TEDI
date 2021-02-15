package com.service;

import com.handler.DeliveryOrdersDTO;
import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import com.model.Users;
import com.repository.DeliveryOrdersRepository;
import com.repository.DetailsDeliveryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderService {

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
    return deliveryOrders;
  }

  public DeliveryOrders saveDeliveryOrders(DeliveryOrders deliveryOrders) {
    return deliveryOrdersRepository.save(deliveryOrders);
  }

  public DeliveryOrders update(Optional<DeliveryOrders> optionalDeliveryOrders, DeliveryOrdersDTO deliveryOrdersDTO, Users customer, Users supplier){
    DeliveryOrders deliveryOrders = optionalDeliveryOrders.get();
    deliveryOrders.updateDeliveryOrders(deliveryOrdersDTO, customer, supplier);
    deliveryOrdersRepository.save(deliveryOrders);
    if( deliveryOrdersDTO.getDetailsDeliveryOrdersList() != null && !deliveryOrdersDTO.getDetailsDeliveryOrdersList().isEmpty()) {
      deliveryOrdersDTO.getDetailsDeliveryOrdersList().forEach(x -> {
        Optional<DetailsDeliveryOrders> detailsDeliveryOrders = detailsDeliveryOrderRepository.findById(x.getDetailsId());
        if (detailsDeliveryOrders.isPresent())
          detailService.updateAll(deliveryOrders, detailsDeliveryOrders.get(), x, customer, supplier);
      });
    }
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
    deliveryOrdersRepository.delete(deliveryOrders);
  }
}
