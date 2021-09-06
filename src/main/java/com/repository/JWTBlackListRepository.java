package com.repository;

import com.model.JWTBlackList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JWTBlackListRepository extends CrudRepository<JWTBlackList, Long> {

  List<JWTBlackList> findAllByExpireTimeBefore(Long data);

  boolean existsByToken(String s);

}
