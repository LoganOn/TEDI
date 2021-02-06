package com.controller;

import com.model.RelationsUsers;
import com.repository.RelationsUsersRepository;
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

@RestController
@RequestMapping(value = "/api/relations", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RelationsController {

    private RelationsUsersRepository relationsRepository;

    private UsersRepository usersRepository;

    @GetMapping
    public ResponseEntity<?> findAllRelations() {
        List<RelationsUsers> relationsList = (List<RelationsUsers>) relationsRepository.findAll();
        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> findAllRelationsByCustomerId(@PathVariable Long id) {
        List<RelationsUsers> relationsList = relationsRepository.findAllByCustomer(usersRepository.findById(id).get());
        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<?> findAllRelationsBySupplierId(@PathVariable Long id) {
        List<RelationsUsers> relationsList = relationsRepository.findAllBySupplier(usersRepository.findById(id).get());
        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}