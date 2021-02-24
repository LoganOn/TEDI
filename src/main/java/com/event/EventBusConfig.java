package com.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.listener.NotificationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class EventBusConfig {

  private NotificationListener notificationListener;

  @Autowired
  public EventBusConfig(NotificationListener notificationListener) {
    this.notificationListener = notificationListener;
  }

  @Bean(destroyMethod="shutdown")
  @ConditionalOnMissingBean
  ListeningExecutorService listeningExecutorService() {
    return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4));
  }


  @Bean
  AsyncEventBus asyncEventBus(final ListeningExecutorService executor) {
    AsyncEventBus asyncEventBus = new AsyncEventBus(executor);
    asyncEventBus.register(notificationListener);
    return asyncEventBus;
  }
}
