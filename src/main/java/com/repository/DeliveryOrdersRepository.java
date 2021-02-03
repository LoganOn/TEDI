package com.repository;

import com.model.DeliveryOrders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrdersRepository extends CrudRepository<DeliveryOrders, Long> {

    List<DeliveryOrders> findAllByUserId1 (Long user);

    List<DeliveryOrders> findAllByUserId2 (Long supplier);

    Optional<DeliveryOrders> findByBaseRef (String baseRef);

    List<DeliveryOrders> findByBaseRefContaining (String baseRef);

    List<DeliveryOrders> findAll(Pageable pageable);
}
