package com.controller;

import com.model.DeliveryOrders;
import com.repository.DeliveryOrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping(value = "/api/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/api/delivery")
@AllArgsConstructor
public class DeliveryOrdersController {

  private final DeliveryOrdersRepository deliveryOrdersRepository;

  @GetMapping("/all")
  public ResponseEntity<?> findAllOrders() {
   List<DeliveryOrders> deliveryOrders = (List<DeliveryOrders>) deliveryOrdersRepository.findAll();
    return new ResponseEntity<>(
            deliveryOrders, deliveryOrders == null ?
            HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

}
