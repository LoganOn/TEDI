package com.controller;

import com.model.DeliveryOrders;
import com.repository.DeliveryOrdersRepository;
import com.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DeliveryOrdersController {

    private DeliveryOrdersRepository deliveryOrdersRepository;

    private UsersRepository usersRepository;

    @GetMapping
    public ResponseEntity<?> findAllOrders() {
        List<DeliveryOrders> deliveryOrders = (List<DeliveryOrders>) deliveryOrdersRepository.findAll();
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
        List<DeliveryOrders> deliveryOrdersFiltering = deliveryOrders.stream().filter(d-> d.getUserId1() == id2).collect(Collectors.toList());
        return new ResponseEntity<>(
                deliveryOrdersFiltering, deliveryOrdersFiltering == null ?
                HttpStatus.NOT_FOUND : deliveryOrdersFiltering.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
    //TODO change to headers
//    @GetMapping("/{baseRef}")
//    public ResponseEntity<?> findOrderByBaseRef(@PathVariable String baseRef) {
//        List<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findByBaseRefContaining(baseRef);
//        return new ResponseEntity<>(
//                deliveryOrders, deliveryOrders == null ?
//                HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
//                HttpStatus.NO_CONTENT : HttpStatus.OK
//        );

}
