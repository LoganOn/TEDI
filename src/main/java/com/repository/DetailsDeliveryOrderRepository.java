package com.repository;

import com.model.DetailsDeliveryOrders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsDeliveryOrderRepository extends CrudRepository<DetailsDeliveryOrders, Long> {
}
