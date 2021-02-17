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

  private final SubscriptionsRepository subscriptionsRepository;

  private final NotificationService notificationService;

  private final UsersRepository usersRepository;

  private final EmailSender emailSender;

  @Subscribe
  public void newDeliveryOrders(DeliveryOrders deliveryOrders) {

    List<Users> usersNotifications = usersRepository.findByNotificationTrue();

    List<Subscriptions> subscriptions = subscriptionsRepository.findByEmailTrue();

    List<Users> usersSubscriptions = new ArrayList<>();
    for (Subscriptions s : subscriptions) {
      usersSubscriptions.add(s.getCustomer());
    }

    newDeliveryOrdersNotifications(deliveryOrders, usersNotifications);
    newDeliveryOrdersSubscriptions(deliveryOrders, usersSubscriptions);
  }

  @Subscribe
  public void newDetailsDeliveryOrders(DetailsDeliveryOrders detailsDeliveryOrders) {
    System.out.println(detailsDeliveryOrders.toString());
  }

  public void newDeliveryOrdersNotifications(DeliveryOrders deliveryOrders, List<Users> usersNotifications) {
    for (Users u : usersNotifications
    ) {
      String content = "";
      if (deliveryOrders.getType().equals("post")) {
        content = "Added new delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer() + "and " + deliveryOrders.getDetailsDeliveryOrdersList().size() + " items";
      } else if (deliveryOrders.getType().equals("put")) {
        content = "Updated delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("patch")) {
        content = "Updated delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("delete")) {
        content = "Deleted delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      }
      notificationService.save(new Notifications(deliveryOrders, null, u, content, false, new Timestamp(System.currentTimeMillis())));
    }
  }

  public void newDeliveryOrdersSubscriptions(DeliveryOrders deliveryOrders, List<Users> usersSubscriptions) {
    for (Users u : usersSubscriptions
    ) {
      String content = "";
      if (deliveryOrders.getType().equals("post")) {
        content = "Added new delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("put")) {
        content = "Updated delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("patch")) {
        content = "Updated delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("delete")) {
        content = "Deleted delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      }
     emailSender.sendEmail(u.getEmail(), "Delivery order number : " + deliveryOrders.getNumberOrderCustomer(), content);
    }
  }
  public void newDetailsDeliveryOrdersNotifications(DeliveryOrders deliveryOrders, List<Users> usersNotifications) {
    for (Users u : usersNotifications
    ) {
      String content = "";
      if (deliveryOrders.getType().equals("post")) {
        content = "Added new delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer() + "and " + deliveryOrders.getDetailsDeliveryOrdersList().size() + " items";
      } else if (deliveryOrders.getType().equals("put")) {
        content = "Updated delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("patch")) {
        content = "Updated delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("delete")) {
        content = "Deleted delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      }
      notificationService.save(new Notifications(deliveryOrders, null, u, content, false, new Timestamp(System.currentTimeMillis())));
    }
  }

  public void newDetailsDeliveryOrdersSubscriptions(DeliveryOrders deliveryOrders, List<Users> usersSubscriptions) {
    for (Users u : usersSubscriptions
    ) {
      String content = "";
      if (deliveryOrders.getType().equals("post")) {
        content = "Added new delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("put")) {
        content = "Updated delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("patch")) {
        content = "Updated delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      } else if (deliveryOrders.getType().equals("delete")) {
        content = "Deleted delivery orders from supplier, with number : " + deliveryOrders.getNumberOrderCustomer();
      }
      emailSender.sendEmail(u.getEmail(), "Delivery order number : " + deliveryOrders.getNumberOrderCustomer(), content);
    }
  }
}
