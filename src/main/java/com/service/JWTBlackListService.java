package com.service;

import com.model.JWTBlackList;
import com.repository.JWTBlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class JWTBlackListService {

  private final JWTBlackListRepository jwtBlackListRepository;

  public List<JWTBlackList> findAllByExpireTimeBefore(Long data) {
    return jwtBlackListRepository.findAllByExpireTimeBefore(data);
  }

  public void deleteAll(List<JWTBlackList> entities) {
    if(entities != null && !entities.isEmpty())
      jwtBlackListRepository.deleteAll(entities);
  }

  public boolean existsByToken(String s) {
    return jwtBlackListRepository.existsByToken(s);
  }

  public JWTBlackList save(String jwtBlackList){
    if(jwtBlackList != null)
      return jwtBlackListRepository.save(jwtBlackList);
    return null;
  }
}
