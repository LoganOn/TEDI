package com.service;

import com.model.Items;
import com.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemsRepository itemsRepository;

  public Items save(Items items){
    return itemsRepository.save(items);
  }

  public Items update(Items items){
    return itemsRepository.save(items);
  }

  public void delete(Items items){
    itemsRepository.delete(items);
  }
}
