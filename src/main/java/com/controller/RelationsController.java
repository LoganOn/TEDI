package com.controller;

import com.exception.BadRequestException;
import com.exception.ResourceNotFoundException;
import com.handler.RelationUsersDTO;
import com.handler.UserMinimalInfo;
import com.model.RelationsUsers;
import com.model.Users;
import com.repository.RelationsUsersRepository;
import com.repository.UsersRepository;
import com.service.RelationService;
import com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/relations", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RelationsController {

    private final String RESOURCE_NOT_FOUND = "Resource not found";

    private final String USER_NOT_EXIST = "Customer or supplier not exist";

    private final String USER_IS_THE_SAME = "Customer and supplier cannot be the same";

    private final String RELATIONS_IS_EXIST = "Relations is exist";

    private final RelationsUsersRepository relationsRepository;

    private final UsersRepository usersRepository;

    private final RelationService relationService;

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> findAllRelations() {
        List<RelationsUsers> relationsList = relationsRepository.findAll();
        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<?> findAllRelationsByUser() {
        UserMinimalInfo userMinimalInfo = userService.getCurrentUser();
        List<RelationsUsers> relationsList;
        if (userMinimalInfo.getRole().equals("customer")) {
            relationsList = relationsRepository.findAllByCustomer(usersRepository.findById(userMinimalInfo.getUserId()).get());
        } else if (userMinimalInfo.getRole().equals("supplier")) {
            relationsList = relationsRepository.findAllByCustomer(usersRepository.findById(userMinimalInfo.getUserId()).get());
        } else {
            relationsList = null;
        }

        return new ResponseEntity<>(
                relationsList, relationsList == null ?
                HttpStatus.NOT_FOUND : relationsList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/without")
    public ResponseEntity<?> findAllWithoutRelationsByUser() {
        UserMinimalInfo userMinimalInfo = userService.getCurrentUser();
        List<Users> usersList;
        List<RelationsUsers> relationsUsersList;
        List<Users> temp = new ArrayList<>();
        if (userMinimalInfo.getRole().equals("customer")) {
            usersList = usersRepository.findByRoleNot("customer");
            relationsUsersList = relationsRepository.findAllByCustomer(usersRepository.findById(userMinimalInfo.getUserId()).get());
            for (Users u : usersList
            ) {
                for (RelationsUsers r : relationsUsersList
                ) {
                    if (r.getSupplier().getUserId() == u.getUserId()) {
                        temp.add(u);
                    }
                }
            }
            usersList.removeAll(temp);
        } else if (userMinimalInfo.getRole().equals("supplier")) {
            usersList = usersRepository.findByRoleNot("supplier");
            relationsUsersList = relationsRepository.findAllBySupplier(usersRepository.findById(userMinimalInfo.getUserId()).get());
            for (Users u : usersList
            ) {
                for (RelationsUsers r : relationsUsersList
                ) {
                    if (r.getCustomer().getUserId() == u.getUserId()) {
                        temp.add(u);
                    }
                }
            }
            usersList.removeAll(temp);
        } else {
            usersList = null;
        }
        return new ResponseEntity<>(
                usersList, usersList == null ?
                HttpStatus.NOT_FOUND : usersList.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addRelations(@RequestBody RelationUsersDTO relationUsersDTO) {
        Integer id = relationsRepository.findAll().size() + 1;
        Optional<Users> customer = usersRepository.findById(Long.valueOf(relationUsersDTO.getCustomerId()));
        Optional<Users> supplier = usersRepository.findById(Long.valueOf(relationUsersDTO.getSupplierId()));
        if (customer.isEmpty() || supplier.isEmpty())
            throw new BadRequestException(USER_NOT_EXIST);
        if (customer.equals(supplier))
            throw new BadRequestException(USER_IS_THE_SAME);
        Optional<RelationsUsers> optional = relationsRepository.findBySupplierAndCustomer(supplier.get(), customer.get());
        if (optional.isPresent())
            throw new BadRequestException(RELATIONS_IS_EXIST);
        relationService.save(customer.get(), supplier.get());
        return new ResponseEntity<>(
                id, id == null ?
                HttpStatus.NOT_FOUND : id == 0 ?
                HttpStatus.NO_CONTENT : HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<RelationsUsers> optionalRelationsUsers = relationsRepository.findById(id);
        if (optionalRelationsUsers.isEmpty()) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        }
        relationService.delete(optionalRelationsUsers.get());
        return new ResponseEntity<>(
                optionalRelationsUsers.get().getRelationUsersId(), optionalRelationsUsers.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }
}