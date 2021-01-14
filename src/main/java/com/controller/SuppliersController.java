package com.controller;

import com.model.Customers;
import com.model.Suppliers;
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
@RequestMapping(value = "/api/suppliers", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class SuppliersController{

    private SuppliersRepository suppliersRepository;

    @GetMapping
    public ResponseEntity<?> findAllSuppliers() {
        List<Suppliers> suppliersList = (List<Suppliers>) suppliersRepository.findAll();
        return new ResponseEntity<>(
                suppliersList, suppliersList == null ?
                HttpStatus.NOT_FOUND : suppliersList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSupplierById(@PathVariable Long id) {
        Optional<Suppliers> supplier = suppliersRepository.findById(id);
        return new ResponseEntity<>(
                supplier, supplier == null ?
                HttpStatus.NOT_FOUND : supplier.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}
