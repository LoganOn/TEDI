package com.repository;

import com.model.DeliveryOrders;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryOrdersRepository extends CrudRepository<DeliveryOrders, Long> {
}
