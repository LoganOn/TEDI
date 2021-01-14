package com.controller;

import com.model.Customers;
import com.repository.CustomersRepository;
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
@RequestMapping(value = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CustomersController {

    private CustomersRepository customersRepository;

    @GetMapping
    public ResponseEntity<?> findAllCustomers() {
        List<Customers> customers = (List<Customers>) customersRepository.findAll();
        return new ResponseEntity<>(
                customers, customers == null ?
                HttpStatus.NOT_FOUND : customers.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {
        Optional<Customers> customer = customersRepository.findById(id);
        return new ResponseEntity<>(
                customer, customer == null ?
                HttpStatus.NOT_FOUND : customer.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}
