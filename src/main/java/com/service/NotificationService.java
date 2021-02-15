package com.service;

import com.model.Notifications;
import com.repository.NotificationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationsRepository notificationsRepository;

  public Notifications save(Notifications notifications){
    return notificationsRepository.save(notifications);
  }

  public void delete(Notifications notifications){
    notificationsRepository.delete(notifications);
  }
}
