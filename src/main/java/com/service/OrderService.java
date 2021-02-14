package com.service;

import com.handler.DeliveryOrdersDTO;
import com.handler.UserSignupDto;
import com.model.DeliveryOrders;
import com.model.Users;
import com.repository.DeliveryOrdersRepository;
import com.repository.DetailsDeliveryOrderRepository;
import com.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderService {


  private final DeliveryOrdersRepository deliveryOrdersRepository;
  private final DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;


  public DeliveryOrders save(DeliveryOrdersDTO deliveryOrdersDTO, Users customer, Users supplier){
    DeliveryOrders deliveryOrders = new DeliveryOrders(deliveryOrdersDTO, customer, supplier);
    deliveryOrdersRepository.save(deliveryOrders);
    deliveryOrders.getDetailsDeliveryOrdersList().forEach(x->{
      x.setParametrs(deliveryOrders);
      detailsDeliveryOrderRepository.save(x);
    });
    return deliveryOrders;
  }

  public DeliveryOrders update(Optional<DeliveryOrders> optionalDeliveryOrders, DeliveryOrdersDTO deliveryOrdersDTO){


    return deliveryOrdersRepository.save(deliveryOrdersDTO);
  }

  public void delete(DeliveryOrders deliveryOrders){
    deliveryOrdersRepository.delete(deliveryOrders);
  }

}
