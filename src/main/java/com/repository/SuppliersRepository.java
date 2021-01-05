package com.repository;

import com.model.Suppliers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppliersRepository extends CrudRepository<Suppliers, Long> {
}
