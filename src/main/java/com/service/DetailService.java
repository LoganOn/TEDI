package com.service;

import com.handler.DeliveryOrdersDTO;
import com.handler.UserSignupDto;
import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import com.model.Users;
import com.repository.DeliveryOrdersRepository;
import com.repository.DetailsDeliveryOrderRepository;
import com.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DetailService {


  private final DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;


//  public DetailsDeliveryOrders save(DeliveryOrdersDTO deliveryOrdersDTO, Users customer, Users supplier){
//    DetailsDeliveryOrders deliveryOrders = new DeliveryOrders(deliveryOrdersDTO, customer, supplier);
//    return deliveryOrdersRepository.save(deliveryOrders);
//  }
//
//  public DeliveryOrders update(DeliveryOrders deliveryOrders){
//    return deliveryOrdersRepository.save(deliveryOrders);
//  }
//
//  public void delete(DeliveryOrders deliveryOrders){
//    deliveryOrdersRepository.delete(deliveryOrders);
//  }

}
