package com.controller;

import com.model.Suppliers;
import com.repository.SuppliersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class SuppliersController{

    private SuppliersRepository suppliersRepository;

    @GetMapping("/all")
    public ResponseEntity<?> findAllDetailsOrders() {
        List<Suppliers> suppliersList = (List<Suppliers>) suppliersRepository.findAll();
        return new ResponseEntity<>(
                suppliersList, suppliersList == null ?
                HttpStatus.NOT_FOUND : suppliersList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}
