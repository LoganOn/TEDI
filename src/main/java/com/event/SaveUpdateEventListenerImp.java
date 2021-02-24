package com.event;

import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;


public class SaveUpdateEventListenerImp implements SaveOrUpdateEventListener {
  @Override
  public void onSaveOrUpdate(SaveOrUpdateEvent saveOrUpdateEvent) throws HibernateException {
    Object obj = saveOrUpdateEvent.getEntity();
    if (obj instanceof DeliveryOrders) {
      System.out.println("mamy update");
    }
    else if (obj instanceof DetailsDeliveryOrders){
      System.out.println("mamy update");
    }
  }
}
