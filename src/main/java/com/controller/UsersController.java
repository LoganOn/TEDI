package com.controller;

import com.model.Users;
import com.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
//@CrossOrigin()
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

//    @PostMapping
//    public ResponseEntity<?> newUser (@RequestBody Users users) {
//        usersRepository.save(users);
//        return new ResponseEntity.created().build();
//        );
//    }

    //TODO findAllUsersByRole
}
