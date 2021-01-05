package com.repository;

import com.model.DetailsDeliveryOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsDeliveryOrderRepository extends CrudRepository<DetailsDeliveryOrder, Long> {
}
