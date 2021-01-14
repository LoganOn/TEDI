package com.repository;

import com.model.RelationsUsers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationsUsersRepository extends CrudRepository<RelationsUsers, Long> {
}
