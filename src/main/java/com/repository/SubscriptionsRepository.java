package com.repository;

import com.model.Subscriptions;
import com.model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionsRepository extends CrudRepository<Subscriptions, Long> {

  List<Subscriptions> findByEmailTrue();
}
