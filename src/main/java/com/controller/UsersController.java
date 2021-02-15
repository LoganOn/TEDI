package com.controller;

import com.exception.BadRequestException;
import com.exception.ResourceNotFoundException;
import com.model.Users;
import com.repository.UsersRepository;
import com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UsersController {

    private final String USER_NOT_EXIST = "Customer or supplier not exist";

    private final String OBJECT_IN_BODY = "The PATCH method does not support updating objects";

    private UsersRepository usersRepository;

    private UserService userService;

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

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty())
            throw new ResourceNotFoundException(USER_NOT_EXIST);
        fields.forEach((k,v) -> {
            if(k.equals("deliveryOrdersList") || k.equals("deliveryOrdersList2") || k.equals("relationsList1") || k.equals("relationsList2")){
                throw new BadRequestException(OBJECT_IN_BODY);
            }
            else{
                Field field = ReflectionUtils.findField(Users.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, optionalUsers.get(), v);
            }
        });
        userService.update(optionalUsers.get());
        return new ResponseEntity<>(
                id, id == null ?
                HttpStatus.NOT_FOUND : id == 0 ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }
    //TODO findAllUsersByRole
}
