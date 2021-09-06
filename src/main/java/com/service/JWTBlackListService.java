package com.service;

import java.util.List;

import com.model.JWTBlackList;
import com.repository.JWTBlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

  public JWTBlackList save(JWTBlackList jwtBlackList){
    if(jwtBlackList != null)
      return jwtBlackListRepository.save(jwtBlackList);
    return null;
  }
}

