package com.service;

import com.handler.BasicDetailsDeliveryOrderDTO;
import com.handler.DetailsDeliveryOrderDTO;
import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import com.repository.DetailsDeliveryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import javax.swing.plaf.basic.BasicArrowButton;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class DetailService {


  private final DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;


  public DetailsDeliveryOrders save(DeliveryOrders deliveryOrder, BasicDetailsDeliveryOrderDTO basicDetailsDeliveryOrderDTO) {
    DetailsDeliveryOrders detailsDeliveryOrders = DetailsDeliveryOrders.toDetailsDeliveryOrders(basicDetailsDeliveryOrderDTO);
    detailsDeliveryOrders.setParametrs(deliveryOrder);
    return detailsDeliveryOrderRepository.save(detailsDeliveryOrders);
  }

//  public Iterable<DetailsDeliveryOrders> saveAll(DeliveryOrders deliveryOrder, List<DetailsDeliveryOrderDTO> detailsDeliveryOrderDTO) {
//    List<DetailsDeliveryOrders> detailsDeliveryOrders = Collections.emptyList();
//    detailsDeliveryOrderDTO.forEach(x -> {
//      detailsDeliveryOrders.add(DetailsDeliveryOrders.toDetailsDeliveryOrders(x));
//    });
//    detailsDeliveryOrders.forEach(x -> {
//      x.setParametrs(deliveryOrder);
//    });
//
//    System.out.println(detailsDeliveryOrders.get(0).toString());
//    return detailsDeliveryOrderRepository.saveAll(detailsDeliveryOrders);
//  }
//-
//  public DeliveryOrders update(DeliveryOrders deliveryOrders){
//    return deliveryOrdersRepository.save(deliveryOrders);
//  }
//
//  public void delete(DeliveryOrders deliveryOrders){
//    deliveryOrdersRepository.delete(deliveryOrders);
//  }

}
