package com.repository;

import com.model.Suppliers;
import org.springframework.data.repository.CrudRepository;

public interface SuppliersRepository extends CrudRepository<Suppliers, Long> {
}
