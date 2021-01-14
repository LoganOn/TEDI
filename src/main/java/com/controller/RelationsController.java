package com.controller;

import com.model.RelationsUsers;
import com.repository.RelationsUsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/relation", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RelationsController {

    private RelationsUsersRepository relationsRepository;

    @GetMapping("/all")
    public ResponseEntity<?> findAllDetailsOrders() {
        List<RelationsUsers> relationsList = (List<RelationsUsers>) relationsRepository.findAll();
        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}