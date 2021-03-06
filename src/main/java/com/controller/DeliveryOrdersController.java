package com.controller;

import com.exception.BadRequestException;
import com.exception.ResourceNotFoundException;
import com.handler.DeliveryOrdersDTO;
import com.handler.DeliveryOrdersPage;
import com.model.DeliveryOrders;
import com.model.MinimalInfo;
import com.model.Users;
import com.repository.DeliveryOrdersRepository;
import com.repository.UsersRepository;
import com.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/delivery", produces = "application/json")
@AllArgsConstructor
public class DeliveryOrdersController {

  private final String USER_NOT_EXIST = "Customer or supplier not exist";

  private final String USER_IS_THE_SAME = "Customer and supplier cannot be the same";

  private final String DELIVERY_ORDERS_NOT_EXIST = "Delivery orders not exist";

  private final String RESOURCE_NOT_FOUND = "Resource not found";

  private final String OBJECT_IN_BODY = "The PATCH method does not support updating objects";

  private final DeliveryOrdersRepository deliveryOrdersRepository;

  private final OrderService orderService;

  private final UsersRepository usersRepository;

  private static final int SIZE = 20;

  @GetMapping("/{id}")
  public ResponseEntity<?> findOrdersById(@PathVariable Long id) {
    Optional<DeliveryOrders> deliveryOrder = deliveryOrdersRepository.findById(id);
    return new ResponseEntity<>(
            deliveryOrder, deliveryOrder.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping("/supplier/{id}")
  public ResponseEntity<?> findAllOrdersBySupplierId(@PathVariable Long id,
                                                     @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
                                                     @RequestParam(required = false, defaultValue = "") String name,
                                                     @RequestParam(required = false, defaultValue = "") String baseRef,
                                                     @RequestParam(required = false, defaultValue = "") String cusNumber) {
    int p = page == null ? 0 : page <= 0 ? 0 : page;
    int s = size == null ? SIZE : size <= 0 ? SIZE : size;
    List<DeliveryOrders> deliveryOrders;
    Optional<Users> users = usersRepository.findById(id);
    deliveryOrders = deliveryOrdersRepository.findAllBySupplier(users.get(), PageRequest.of(p, s));
    deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getSupplier().getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getBaseRef().toLowerCase().contains(baseRef.toLowerCase())).collect(Collectors.toList());
    deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getNumberOrderCustomer().toLowerCase().contains(cusNumber.toLowerCase())).collect(Collectors.toList());
    DeliveryOrdersPage deliveryOrdersPage = new DeliveryOrdersPage(deliveryOrdersRepository.countBySupplier(users.get()) , deliveryOrders);
    return new ResponseEntity<>(
            deliveryOrdersPage, deliveryOrdersPage == null ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping("/customer/{id}")
  public ResponseEntity<?> findAllOrdersByCustomerId(@PathVariable Long id,
                                                     @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
                                                     @RequestParam(required = false, defaultValue = "") String name,
                                                     @RequestParam(required = false, defaultValue = "") String baseRef,
                                                     @RequestParam(required = false, defaultValue = "") String cusNumber) {
    int p = page == null ? 0 : page <= 0 ? 0 : page;
    int s = size == null ? SIZE : size <= 0 ? SIZE : size;
    List<DeliveryOrders> deliveryOrders;
    Optional<Users> users = usersRepository.findById(id);
    deliveryOrders = deliveryOrdersRepository.findAllByCustomer(users.get(), PageRequest.of(p, s));
    deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getSupplier().getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getBaseRef().toLowerCase().contains(baseRef.toLowerCase())).collect(Collectors.toList());
    deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getNumberOrderCustomer().toLowerCase().contains(cusNumber.toLowerCase())).collect(Collectors.toList());
    DeliveryOrdersPage deliveryOrdersPage = new DeliveryOrdersPage(deliveryOrdersRepository.countByCustomer(users.get()), deliveryOrders);
    return new ResponseEntity<>(
            deliveryOrdersPage, deliveryOrdersPage == null ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  //TODO sprawdzic ten required
  @GetMapping("/customer/{id1}/supplier/{id2}")
  public ResponseEntity<?> findAllOrdersByCustomerIdAndSupplierId(@PathVariable Long id1, @PathVariable Long id2,
                                                                  @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {

    int p = page == null ? 0 : page <= 0 ? 0 : page;
    int s = size == null ? 1 : size <= 20 ? SIZE : size;
    List<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findAllByCustomer(usersRepository.findById(id1).get(), PageRequest.of(p, s));
    List<DeliveryOrders> deliveryOrdersFiltering = deliveryOrders.stream().filter(d -> d.getSupplier().getUserId() == id2).collect(Collectors.toList());
    return new ResponseEntity<>(
            deliveryOrdersFiltering, deliveryOrdersFiltering == null ?
            HttpStatus.NOT_FOUND : deliveryOrdersFiltering.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping()
  public ResponseEntity<?> findOrdersByUserandBaseRefandCustomerNumber(@RequestBody MinimalInfo minimalInfo, @RequestParam(required = false, defaultValue = "") String name,
                                                                       @RequestParam(required = false, defaultValue = "") String baseRef,
                                                                       @RequestParam(required = false, defaultValue = "") String cusNumber,
                                                                       @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    List<DeliveryOrders> deliveryOrders;
    int p = page == null ? 0 : page <= 0 ? 0 : page;
    int s = size == null ? 1 : size <= 20 ? SIZE : size;

    if (minimalInfo.getRole().equals("customer")) {
      deliveryOrders = deliveryOrdersRepository.findAllByCustomer(usersRepository.findById(minimalInfo.getUserid()).get(), PageRequest.of(p, s));
      deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getSupplier().getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
      deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getBaseRef().toLowerCase().contains(baseRef.toLowerCase())).collect(Collectors.toList());
      deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getNumberOrderCustomer().toLowerCase().contains(cusNumber.toLowerCase())).collect(Collectors.toList());
    } else if (minimalInfo.getRole().equals("supplier")) {
      deliveryOrders = deliveryOrdersRepository.findAllBySupplier(usersRepository.findById(minimalInfo.getUserid()).get(), PageRequest.of(p, s));
      deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getSupplier().getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
      deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getBaseRef().toLowerCase().contains(baseRef.toLowerCase())).collect(Collectors.toList());
      deliveryOrders = deliveryOrders.stream().filter(delivery -> delivery.getNumberOrderCustomer().toLowerCase().contains(cusNumber.toLowerCase())).collect(Collectors.toList());
    } else {
      deliveryOrders = null;
    }

    return new ResponseEntity<>(
            deliveryOrders, deliveryOrders == null ?
            HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @PostMapping(consumes = "application/json")
  public ResponseEntity addDeliveryOrders(@RequestBody DeliveryOrdersDTO deliveryOrdersDTO) {
    Integer id = deliveryOrdersRepository.findAll().size() + 1;
    Optional<Users> customer = usersRepository.findById(deliveryOrdersDTO.getCustomer().getUserId());
    Optional<Users> supplier = usersRepository.findById(deliveryOrdersDTO.getSupplier().getUserId());
    if (customer.isEmpty() || supplier.isEmpty())
      throw new BadRequestException(USER_NOT_EXIST);
    if (customer.equals(supplier))
      throw new BadRequestException(USER_IS_THE_SAME);
    orderService.save(deliveryOrdersDTO, customer.get(), supplier.get());
    return new ResponseEntity<>(
            id, id == null ?
            HttpStatus.NOT_FOUND : id == 0 ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @PutMapping(value = "/{id}", consumes = "application/json")
  public ResponseEntity updateDeliveryOrders(@PathVariable Long id, @RequestBody DeliveryOrdersDTO deliveryOrdersDTO) {
    Optional<DeliveryOrders> optionalDeliveryOrders = deliveryOrdersRepository.findById(id);
    if (optionalDeliveryOrders.isEmpty())
      throw new ResourceNotFoundException(DELIVERY_ORDERS_NOT_EXIST);
    Optional<Users> customer = usersRepository.findById(deliveryOrdersDTO.getCustomer().getUserId());
    Optional<Users> supplier = usersRepository.findById(deliveryOrdersDTO.getSupplier().getUserId());
    if (customer.isEmpty() || supplier.isEmpty())
      throw new BadRequestException(USER_NOT_EXIST);
    if (customer.equals(supplier))
      throw new BadRequestException(USER_IS_THE_SAME);
    orderService.update(optionalDeliveryOrders, deliveryOrdersDTO, customer.get(), supplier.get());
    return new ResponseEntity<>(
            id, id == null ?
            HttpStatus.NOT_FOUND : id == 0 ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @PatchMapping(value = "/{id}", consumes = "application/json")
  public ResponseEntity updateFieldsDeliveryOrders(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
    Optional<DeliveryOrders> optionalDeliveryOrders = deliveryOrdersRepository.findById(id);
    if (optionalDeliveryOrders.isEmpty())
      throw new ResourceNotFoundException(DELIVERY_ORDERS_NOT_EXIST);
    fields.forEach((k,v) -> {
        if(k.equals("supplier") || k.equals("customer") || k.equals("detailsDeliveryOrdersList")){
          throw new BadRequestException(OBJECT_IN_BODY);
        }
        else{
        Field field = ReflectionUtils.findField(DeliveryOrders.class, (String) k);
        field.setAccessible(true);
        ReflectionUtils.setField(field, optionalDeliveryOrders.get(), v);
      }
    });
    optionalDeliveryOrders.get().setModifyDate(new Timestamp(System.currentTimeMillis()));
    orderService.saveDeliveryOrders(optionalDeliveryOrders.get());
    return new ResponseEntity<>(
            id, id == null ?
            HttpStatus.NOT_FOUND : id == 0 ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDeliveryOrders(@PathVariable Long id) {
    Optional<DeliveryOrders> optionalDeliveryOrders = deliveryOrdersRepository.findById(id);
    if (optionalDeliveryOrders.isEmpty()) {
      throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }
    orderService.delete(optionalDeliveryOrders.get());
    return new ResponseEntity<>(
            optionalDeliveryOrders.get().getDeliveryOrderId(), optionalDeliveryOrders.isEmpty() ?
            HttpStatus.NOT_FOUND : HttpStatus.OK
    );
  }
}
