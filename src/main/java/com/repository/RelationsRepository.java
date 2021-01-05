package com.repository;

import com.model.Relations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationsRepository extends CrudRepository<Relations, Long> {
}
