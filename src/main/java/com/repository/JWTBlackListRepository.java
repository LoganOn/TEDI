package com.repository;

import java.util.List;

import com.model.JWTBlackList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTBlackListRepository extends CrudRepository<JWTBlackList, String>{
  List<JWTBlackList> findAllByExpireTimeBefore(Long data);
  boolean existsByToken(String s);
}
