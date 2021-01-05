package com.controller;

import com.model.Customers;
import com.repository.CustomersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CustomersController {

    private CustomersRepository customersRepository;

    @GetMapping("/all")
    public ResponseEntity<?> findAllCustomers() {
        List<Customers> customers = (List<Customers>) customersRepository.findAll();
        return new ResponseEntity<>(
                customers, customers == null ?
                HttpStatus.NOT_FOUND : customers.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}
