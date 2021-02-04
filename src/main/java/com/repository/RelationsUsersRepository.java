package com.repository;

import com.model.RelationsUsers;
import com.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationsUsersRepository extends CrudRepository<RelationsUsers, Long> {

  List<RelationsUsers> findAllBySupplier (Users supplier);

  List<RelationsUsers> findAllByCustomer (Users customer);
}
