package com.controller;

import com.exception.BadRequestException;
import com.handler.DeliveryOrdersDTO;
import com.model.DeliveryOrders;
import com.model.Notifications;
import com.model.Users;
import com.repository.NotificationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/notifications", produces = "application/json")
@AllArgsConstructor
public class NotificationsController {

  private final NotificationsRepository notificationsRepository;

  @GetMapping("/{id}")
  public ResponseEntity<?> findNotificationById(@PathVariable Long id) {
    Optional<Notifications> notification = notificationsRepository.findById(id);
    return new ResponseEntity<>(
            notification, notification.isEmpty() ?
            HttpStatus.NOT_FOUND : HttpStatus.OK
    );
  }

//  @PostMapping(consumes = "application/json")
//  public ResponseEntity addDeliveryOrders(@RequestBody Notifications notifications) {
//    Integer id = notificationsRepository.findAll().size() + 1;
//    Optional<Users> customer = usersRepository.findById(deliveryOrdersDTO.getCustomer().getUserId());
//    Optional<Users> supplier = usersRepository.findById(deliveryOrdersDTO.getSupplier().getUserId());
//    if (customer.isEmpty() || supplier.isEmpty())
//      throw new BadRequestException(USER_NOT_EXIST);
//    if (customer.equals(supplier))
//      throw new BadRequestException(USER_IS_THE_SAME);
//    orderService.save(deliveryOrdersDTO, customer.get(), supplier.get());
//    return new ResponseEntity<>(
//            id, id == null ?
//            HttpStatus.NOT_FOUND : id == 0 ?
//            HttpStatus.NO_CONTENT : HttpStatus.OK
//    );
//  }
}
