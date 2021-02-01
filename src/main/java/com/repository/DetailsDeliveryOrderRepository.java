package com.repository;

import com.model.DetailsDeliveryOrders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsDeliveryOrderRepository extends CrudRepository<DetailsDeliveryOrders, Long> {

  List<DetailsDeliveryOrders> findAllByDeliveryOrder (Long deliveryOrderId);
}
