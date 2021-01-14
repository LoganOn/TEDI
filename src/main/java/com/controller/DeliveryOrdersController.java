package com.controller;

import com.model.DeliveryOrders;
import com.repository.CustomersRepository;
import com.repository.DeliveryOrdersRepository;
import com.repository.SuppliersRepository;
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

@RestController
@RequestMapping(value = "/api/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DeliveryOrdersController {

    private DeliveryOrdersRepository deliveryOrdersRepository;

    private CustomersRepository customersRepository;

    private SuppliersRepository suppliersRepository;

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
        List<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findAllBySupplier(suppliersRepository.findById(id));
        return new ResponseEntity<>(
                deliveryOrders, deliveryOrders == null ?
                HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> findAllOrdersByCustomerId(@PathVariable Long id) {
        List<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findAllByCustomer(customersRepository.findById(id));
        return new ResponseEntity<>(
                deliveryOrders, deliveryOrders == null ?
                HttpStatus.NOT_FOUND : deliveryOrders.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}
