package com.controller;

import com.model.DeliveryOrders;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/delivery", produces = "application/json")
@AllArgsConstructor
public class ItemsController {

  @GetMapping("/{id}")
  public ResponseEntity<?> findOrdersById(@PathVariable Long id) {
    Optional<DeliveryOrders> deliveryOrder = deliveryOrdersRepository.findById(id);
    return new ResponseEntity<>(
            deliveryOrder, deliveryOrder.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }
}
