package com.controller;

import com.exception.ResourceNotFoundException;
import com.model.RelationsUsers;
import com.repository.RelationsUsersRepository;
import com.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/relations", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RelationsController {

    private final String RESOURCE_NOT_FOUND= "Resource not found";

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<RelationsUsers> optionalRelationsUsers = relationsRepository.findById(id);
        if(optionalRelationsUsers.isEmpty()){
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        }
        relationsRepository.delete(optionalRelationsUsers.get());
        return new ResponseEntity<>(
                optionalRelationsUsers.get().getRelationUsersId(), optionalRelationsUsers.isEmpty() ?
                HttpStatus.NOT_FOUND : optionalRelationsUsers.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}