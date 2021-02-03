package com.controller;

import com.model.DeliveryOrders;
import com.model.Users;
import com.repository.DeliveryOrdersRepository;
import com.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DeliveryOrdersController {

  private final DeliveryOrdersRepository deliveryOrdersRepository;

  private final UsersRepository usersRepository;

  private static final int SIZE = 20;

  @GetMapping
  public ResponseEntity<?> findAllOrders(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    int p = page == null ? 1 : page <= 0 ? 1 : page;
    int s = size == null ? 1 : size <= 20 ? SIZE : size;
    List<DeliveryOrders> deliveryOrders = (List<DeliveryOrders>) deliveryOrdersRepository.findAll(PageRequest.of(p - 1, s));
    return new ResponseEntity<>(
            deliveryOrders, deliveryOrders == null ?
            HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findOrdersById(@PathVariable Long id) {
    Optional<DeliveryOrders> deliveryOrder = deliveryOrdersRepository.findById(id);
    return new ResponseEntity<>(
            deliveryOrder, deliveryOrder == null ?
            HttpStatus.NOT_FOUND : deliveryOrder.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping("/supplier/{id}")
  public ResponseEntity<?> findAllOrdersBySupplierId(@PathVariable Long id) {
    List<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findAllByUserId1(usersRepository.findById(id).get().getUserId());
    return new ResponseEntity<>(
            deliveryOrders, deliveryOrders == null ?
            HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping("/customer/{id}")
  public ResponseEntity<?> findAllOrdersByCustomerId(@PathVariable Long id) {
    List<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findAllByUserId2(usersRepository.findById(id).get().getUserId());
    return new ResponseEntity<>(
            deliveryOrders, deliveryOrders == null ?
            HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping("/customer/{id1}/supplier/{id2}")
  public ResponseEntity<?> findAllOrdersByCustomerIdAndSupplierId(@PathVariable Long id1, @PathVariable Long id2) {
    List<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findAllByUserId2(usersRepository.findById(id1).get().getUserId());
    List<DeliveryOrders> deliveryOrdersFiltering = deliveryOrders.stream().filter(d -> d.getUserId1() == id2).collect(Collectors.toList());
    return new ResponseEntity<>(
            deliveryOrdersFiltering, deliveryOrdersFiltering == null ?
            HttpStatus.NOT_FOUND : deliveryOrdersFiltering.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  //TODO change to headers
  @GetMapping
  public ResponseEntity<?> findOrdersByUserandBaseRefandCustomerNumber(@RequestParam(required = false, defaultValue = "") String name,
                                                                       @RequestParam(required = false, defaultValue = "") String baseRef,
                                                                       @RequestParam(required = false, defaultValue = "") String CusNumber) {
    List<DeliveryOrders> deliveryOrders;
    if(!name.isBlank()) {
      Users users = usersRepository.fin
    }
    List<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findByBaseRefContaining(baseRef);
    return new ResponseEntity<>(
            deliveryOrders, deliveryOrders == null ?
            HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }
}
