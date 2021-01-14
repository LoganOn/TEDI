package com.repository;

import com.model.DeliveryOrders;
import com.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrdersRepository extends CrudRepository<DeliveryOrders, Long> {

    List<DeliveryOrders> findAllByUserId1 (Optional<Users> customer);

    List<DeliveryOrders> findAllByUserId2 (Optional<Users> supplier);
}
