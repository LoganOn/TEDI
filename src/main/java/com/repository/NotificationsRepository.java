package com.repository;

import com.model.Notifications;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository  extends CrudRepository<Notifications, Long> {
}
