package com.repository;

import com.model.Notifications;
import com.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository  extends CrudRepository<Notifications, Long> {

  List<Notifications> findByCustomer(Users customer);
}
