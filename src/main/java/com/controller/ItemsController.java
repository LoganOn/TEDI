package com.controller;

import com.exception.BadRequestException;
import com.exception.ResourceNotFoundException;
import com.handler.DeliveryOrdersDTO;
import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import com.model.Items;
import com.model.Users;
import com.repository.ItemsRepository;
import com.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.FetchProfile;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/items", produces = "application/json")
@AllArgsConstructor
public class ItemsController {

  private final String RESOURCE_NOT_FOUND = "Resource not found";

  private final String ITEM_NOT_EXIST = "Item not exist";

  private final String OBJECT_IN_BODY = "The PATCH method does not support updating objects";

  private final ItemsRepository itemsRepository;

  private final ItemService itemService;

  @GetMapping
  public ResponseEntity<?> findAllItems() {
    List<Items> items = itemsRepository.findAll();
    return new ResponseEntity<>(
            items, items.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findItemById(@PathVariable Long id) {
    Optional<Items> items = itemsRepository.findById(id);
    return new ResponseEntity<>(
            items, items.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @PostMapping(consumes = "application/json")
  public ResponseEntity addItems(@RequestBody Items items) {
    Integer id = itemsRepository.findAll().size() + 1;
    itemService.save(items);
    return new ResponseEntity<>(
            id, id == 0 ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @PatchMapping(value = "/{id}", consumes = "application/json")
  public ResponseEntity updateItems(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
    Optional<Items> optionalItems = itemsRepository.findById(id);
    if (optionalItems.isEmpty())
      throw new ResourceNotFoundException(ITEM_NOT_EXIST);
    fields.forEach((k,v) -> {
      if(k.equals("supplier") || k.equals("customer") || k.equals("deliveryOrder")){
        throw new BadRequestException(OBJECT_IN_BODY);
      }
      else{
        Field field = ReflectionUtils.findField(Items.class, (String) k);
        field.setAccessible(true);
        ReflectionUtils.setField(field, optionalItems.get(), v);
      }
    });
//    optionalItems.get().setModifyDate(new Timestamp(System.currentTimeMillis()));
    itemService.save(optionalItems.get());
    return new ResponseEntity<>(
            id, id == null ?
            HttpStatus.NOT_FOUND : id == 0 ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteItems(@PathVariable Long id) {
    Optional<Items> items = itemsRepository.findById(id);
    if (items.isEmpty()) {
      throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }
    itemService.delete(items.get());
    return new ResponseEntity<>(
            items, items.isEmpty() ?
            HttpStatus.NO_CONTENT : HttpStatus.OK
    );
  }
}
