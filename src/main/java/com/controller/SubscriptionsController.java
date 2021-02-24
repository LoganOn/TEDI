package com.controller;

import com.model.Notifications;
import com.model.Subscriptions;
import com.repository.NotificationsRepository;
import com.repository.SubscriptionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/subscriptions", produces = "application/json")
@AllArgsConstructor
public class SubscriptionsController {

  private final SubscriptionsRepository subscriptionsRepository;

  @GetMapping("/{id}")
  public ResponseEntity<?> findSubscriptionById(@PathVariable Long id) {
    Optional<Subscriptions> subscriptions = subscriptionsRepository.findById(id);
    return new ResponseEntity<>(
            subscriptions, subscriptions.isEmpty() ?
            HttpStatus.NOT_FOUND : HttpStatus.OK
    );
  }
}
