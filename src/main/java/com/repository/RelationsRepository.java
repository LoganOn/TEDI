package com.repository;

import com.model.Customers;
import com.model.Relations;
import com.model.Suppliers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationsRepository extends CrudRepository<Relations, Long> {

    List<Relations> findAllByCustomer (Optional<Customers> customer);

    List<Relations> findAllBySupplier (Optional<Suppliers> supplier);
}
