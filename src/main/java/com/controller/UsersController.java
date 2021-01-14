package com.controller;

import com.model.Users;
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
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UsersController {

    private UsersRepository usersRepository;

    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        List<Users> users = (List<Users>) usersRepository.findAll();
        return new ResponseEntity<>(
                users, users == null ?
                HttpStatus.NOT_FOUND : users.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<Users> user = usersRepository.findById(id);
        return new ResponseEntity<>(
                user, user == null ?
                HttpStatus.NOT_FOUND : user.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
}
