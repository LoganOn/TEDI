package com.controller;

import com.model.Customers;
import com.model.Relations;
import com.repository.CustomersRepository;
import com.repository.RelationsRepository;
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

@RestController
@RequestMapping(value = "/api/relations", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RelationsController {

    private RelationsRepository relationsRepository;

    private CustomersRepository customersRepository;

    private SuppliersRepository suppliersRepository;

    @GetMapping
    public ResponseEntity<?> findAllRelations() {
        List<Relations> relationsList = (List<Relations>) relationsRepository.findAll();
        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> findAllRelationsByCustomerId(@PathVariable Long id) {
        List<Relations> relationsList = (List<Relations>) relationsRepository.findAllByCustomer(customersRepository.findById(id));
        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<?> findAllRelationsBySupplierId(@PathVariable Long id) {
        List<Relations> relationsList = (List<Relations>) relationsRepository.findAllBySupplier(suppliersRepository.findById(id));
        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}