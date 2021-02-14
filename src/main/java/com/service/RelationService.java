package com.service;

import com.model.RelationsUsers;
import com.model.Users;
import com.repository.RelationsUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelationService {

  private final RelationsUsersRepository relationsUsersRepository;

  public RelationsUsers save(Users customer, Users supplier) {
    RelationsUsers relationsUsers = new RelationsUsers(customer, supplier, true);
    return relationsUsersRepository.save(relationsUsers);
  }
}
