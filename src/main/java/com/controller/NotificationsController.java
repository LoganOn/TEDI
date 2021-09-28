package com.controller;

import com.exception.BadRequestException;
import com.handler.DeliveryOrdersDTO;
import com.handler.UserMinimalInfo;
import com.model.DeliveryOrders;
import com.model.Notifications;
import com.model.Users;
import com.repository.NotificationsRepository;
import com.repository.UsersRepository;
import com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/notifications", produces = "application/json")
@AllArgsConstructor
public class NotificationsController {

  private final NotificationsRepository notificationsRepository;

  private final UsersRepository usersRepository;

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<?> findNotificationById(@PathVariable Long id) {
    Optional<Notifications> notification = notificationsRepository.findById(id);
    return new ResponseEntity<>(
            notification, notification.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping()
  public ResponseEntity<?> findNotificationByUser() {
    UserMinimalInfo userMinimalInfo = userService.getCurrentUser();
    List<Notifications> notification = notificationsRepository.findByCustomer(usersRepository.findById(userMinimalInfo.getUserId()).get());
    return new ResponseEntity<>(
            notification, notification.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }
}
