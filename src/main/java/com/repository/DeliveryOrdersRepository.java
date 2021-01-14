package com.repository;

import com.model.Customers;
import com.model.DeliveryOrders;
import com.model.Relations;
import com.model.Suppliers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrdersRepository extends CrudRepository<DeliveryOrders, Long> {

    List<DeliveryOrders> findAllByCustomer (Optional<Customers> customer);

    List<DeliveryOrders> findAllBySupplier (Optional<Suppliers> supplier);
}
