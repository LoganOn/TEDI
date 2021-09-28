package com.scheduler;

import java.util.Date;
import java.util.List;

import com.model.JWTBlackList;
import com.service.JWTBlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTCleaner {
  private final JWTBlackListService jwtBlackListService;

  @Scheduled(cron="0 0 0 1 1/1 *")
  public void deleteExpiredTokens(){
    Long time = new Date().getTime();
    List<JWTBlackList> jwtBlackLists = jwtBlackListService.findAllByExpireTimeBefore(time);
    jwtBlackListService.deleteAll(jwtBlackLists);
  }
}
