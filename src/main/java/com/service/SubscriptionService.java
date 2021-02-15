package com.service;

import com.model.Subscriptions;
import com.repository.SubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionService {

  private final SubscriptionsRepository subscriptionsRepository;

  public Subscriptions save(Subscriptions subscriptions){
    return subscriptionsRepository.save(subscriptions);
  }

  public void delete(Subscriptions subscriptions){
    subscriptionsRepository.delete(subscriptions);
  }
}
