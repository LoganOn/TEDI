package com.repository;

import com.model.DeliveryOrders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryOrdersRepository extends CrudRepository<DeliveryOrders, Long> {
}
