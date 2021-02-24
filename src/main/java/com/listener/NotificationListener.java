package com.listener;

import com.google.common.eventbus.Subscribe;
import com.model.*;
import com.repository.SubscriptionsRepository;
import com.repository.UsersRepository;
import com.service.EmailSender;
import com.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationListener {

  private final String ADDED_NEW_DELIVERY_ORDERS = "Dodano nowe potwierdzenie zamówienia o numerze: ";

  private final String UPDATED_DELIVERY_ORDERS = "Zaktualizowano potwierdzenie zamówienia o numerze: ";

  private final String DELETED_DELIVERY_ORDERS = "Usunięto potwierdzenie zamówienia o numerze: ";

  private final String ADDED_NEW_DETAILS_DELIVERY_ORDERS = "Dodano nową pozycje do potwierdzenie zamówienia o numerze: ";

  private final String UPDATED_DETAILS_DELIVERY_ORDERS = "Zaktualizowano pozycję w potwierdzeniu zamówienia o numerze: ";

  private final String DELETED_DETAILS_DELIVERY_ORDERS = "Usunięto pozycję w potwierdzenie zamówienia o numerze: ";

  private final SubscriptionsRepository subscriptionsRepository;

  private final NotificationService notificationService;

  private final UsersRepository usersRepository;

  private final EmailSender emailSender;

  @Subscribe
  public void newDeliveryOrders(DeliveryOrders deliveryOrders) {
    List<Users> usersNotifications = usersRepository.findByNotificationTrue();
    newDeliveryOrdersNotifications(deliveryOrders, usersNotifications);
  }

  @Subscribe
  public void newDetailsDeliveryOrders(DetailsDeliveryOrders detailsDeliveryOrders) {
    List<Users> usersNotifications = usersRepository.findByNotificationTrue();
    List<Subscriptions> subscriptions = subscriptionsRepository.findByEmailTrue();

    List<Users> usersSubscriptions = new ArrayList<>();
    for (Subscriptions s : subscriptions) {
      usersSubscriptions.add(s.getCustomer());
    }
    newDetailsDeliveryOrdersNotifications(detailsDeliveryOrders, usersNotifications);
    newDetailsDeliveryOrdersSubscriptions(detailsDeliveryOrders, usersSubscriptions);
  }

  public void newDeliveryOrdersNotifications(DeliveryOrders deliveryOrders, List<Users> usersNotifications) {
    for (Users u : usersNotifications
    ) {
      String content = "";
      if (deliveryOrders.getType().equals("post")) {
        content = ADDED_NEW_DELIVERY_ORDERS + deliveryOrders.getNumberOrderCustomer() + " z " + deliveryOrders.getDetailsDeliveryOrdersList().size() + " pozycjami";
      } else if (deliveryOrders.getType().equals("put")) {
        content = UPDATED_DELIVERY_ORDERS + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("patch")) {
        content = UPDATED_DELIVERY_ORDERS + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("delete")) {
        content = DELETED_DELIVERY_ORDERS + deliveryOrders.getNumberOrderCustomer();
      }
      notificationService.save(new Notifications(deliveryOrders, null, u, content, false, new Timestamp(System.currentTimeMillis())));
    }
  }

  public void newDetailsDeliveryOrdersNotifications(DetailsDeliveryOrders detailsDeliveryOrders, List<Users> usersNotifications) {
    for (Users u : usersNotifications
    ) {
      String content = "";
      if (detailsDeliveryOrders.getType().equals("post")) {
        content = ADDED_NEW_DETAILS_DELIVERY_ORDERS + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer();
      } else if (detailsDeliveryOrders.getType().equals("put")) {
        content = UPDATED_DETAILS_DELIVERY_ORDERS + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer() ;
      } else if (detailsDeliveryOrders.getType().equals("patch")) {
        content = UPDATED_DETAILS_DELIVERY_ORDERS + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer() ;
      } else if (detailsDeliveryOrders.getType().equals("delete")) {
        content = DELETED_DETAILS_DELIVERY_ORDERS + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer() ;
      }
      notificationService.save(new Notifications(null, detailsDeliveryOrders, u, content, false, new Timestamp(System.currentTimeMillis())));
    }
  }

  public void newDetailsDeliveryOrdersSubscriptions(DetailsDeliveryOrders detailsDeliveryOrders, List<Users> usersSubscriptions) {
    for (Users u : usersSubscriptions
    ) {
      String content = "";
      if (detailsDeliveryOrders.getType().equals("post")) {
        content = ADDED_NEW_DETAILS_DELIVERY_ORDERS + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer();
      } else if (detailsDeliveryOrders.getType().equals("put")) {
        content = UPDATED_DETAILS_DELIVERY_ORDERS + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer();
      } else if (detailsDeliveryOrders.getType().equals("patch")) {
        content = UPDATED_DETAILS_DELIVERY_ORDERS + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer();
      } else if (detailsDeliveryOrders.getType().equals("delete")) {
        content = DELETED_DETAILS_DELIVERY_ORDERS + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer();
      }
      System.out.println(u.getEmail() + "TEDI - Potwierdzenie zamówienia dostawy: " + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer() +  content);
      emailSender.sendEmail(u.getEmail(), "TEDI - Potwierdzenie zamówienia dostawy: " + detailsDeliveryOrders.getDeliveryOrder().getNumberOrderCustomer(), content);
    }
  }
}
