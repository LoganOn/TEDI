package com.controller;

import com.exception.BadRequestException;
import com.exception.ResourceNotFoundException;
import com.handler.BasicDetailsDeliveryOrderDTO;
import com.handler.DeliveryOrdersDTO;
import com.handler.DetailsDeliveryOrderDTO;
import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import com.model.Users;
import com.repository.DeliveryOrdersRepository;
import com.repository.DetailsDeliveryOrderRepository;
import com.repository.UsersRepository;
import com.service.DetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/details", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DetailsDeliveryOrderController {

    private final String DELIVERY_ORDER_NOT_EXIST = "Delivery order not exist";

    private final String RESOURCE_NOT_FOUND = "Resource not found";

    private final String DELIVERY_ORDERS_NOT_EXIST = "Delivery orders not exist";

    private final String DETAILS_DELIVERY_ORDERS_NOT_EXIST = "Details delivery orders not exist";

    private final String USER_NOT_EXIST = "Customer or supplier not exist";

    private final String USER_IS_THE_SAME = "Customer and supplier cannot be the same";

    private final String OBJECT_IN_BODY = "The PATCH method does not support updating objects";

    private final DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;

    private final DeliveryOrdersRepository deliveryOrdersRepository;

    private final UsersRepository usersRepository;

    private final DetailService detailService;

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<?> findAllDetailsOrders() {
        List<DetailsDeliveryOrders> detailsDeliveryOrders = (List<DetailsDeliveryOrders>) detailsDeliveryOrderRepository.findAll();
        System.out.println(detailsDeliveryOrders.get(0).toString());
        return new ResponseEntity<>(
                detailsDeliveryOrders, detailsDeliveryOrders == null ?
                HttpStatus.NOT_FOUND : detailsDeliveryOrders.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDetailsOrderByDeliveryId(@PathVariable Long id) {
        List<DetailsDeliveryOrders> detailsDeliveryOrder = detailsDeliveryOrderRepository.findAllByDeliveryOrder(deliveryOrdersRepository.findById(id).get());
        return new ResponseEntity<>(
                detailsDeliveryOrder, detailsDeliveryOrder == null ?
                HttpStatus.NOT_FOUND : detailsDeliveryOrder.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<?> addDetailsDeliveryOrders(@RequestBody DetailsDeliveryOrderDTO detailsDeliveryOrderDTO) {
        List <Integer> id = new ArrayList<Integer>();

        for (BasicDetailsDeliveryOrderDTO d: detailsDeliveryOrderDTO.getDetailsDeliveryOrders()
             ) {
        id.add(detailsDeliveryOrderRepository.findAll().size() + 1);
        Optional<DeliveryOrders> deliveryOrders = deliveryOrdersRepository.findById(Long.valueOf(detailsDeliveryOrderDTO.getDeliverOrderID()));
        if (deliveryOrders.isEmpty())
            throw new BadRequestException(DELIVERY_ORDER_NOT_EXIST);
        detailService.save(deliveryOrders.get(), d);
        }
        return new ResponseEntity<>(
                id, id == null ?
                HttpStatus.NOT_FOUND : id.isEmpty()?
                HttpStatus.NO_CONTENT : HttpStatus.CREATED
        );
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity updateDetailsDeliveryOrders(@PathVariable Long id, @RequestBody DeliveryOrdersDTO.DetailsDeliveryOrdersList detailsDeliveryOrdersList) {
        Optional<DetailsDeliveryOrders> optionalDetails = detailsDeliveryOrderRepository.findById(id);
        Optional<DeliveryOrders> optionalDeliveryOrders = deliveryOrdersRepository.findById(id);
        if (optionalDeliveryOrders.isEmpty())
            throw new ResourceNotFoundException(DELIVERY_ORDERS_NOT_EXIST);
        if (optionalDetails.isEmpty())
            throw new ResourceNotFoundException(DETAILS_DELIVERY_ORDERS_NOT_EXIST);
        Optional<Users> customer = usersRepository.findById(optionalDeliveryOrders.get().getCustomer().getUserId());
        Optional<Users> supplier = usersRepository.findById(optionalDeliveryOrders.get().getSupplier().getUserId());
        if (customer.isEmpty() || supplier.isEmpty())
            throw new BadRequestException(USER_NOT_EXIST);
        if (customer.equals(supplier))
            throw new BadRequestException(USER_IS_THE_SAME);
        detailService.update(optionalDeliveryOrders.get(), detailsDeliveryOrdersList, customer.get(), supplier.get());
        return new ResponseEntity<>(
                id, id == null ?
                HttpStatus.NOT_FOUND : id == 0 ?
                HttpStatus.NO_CONTENT : HttpStatus.CREATED
        );
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity updateDetailsDeliveryOrders(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Optional<DetailsDeliveryOrders> optionalDetailsDeliveryOrders = detailsDeliveryOrderRepository.findById(id);
        if (optionalDetailsDeliveryOrders.isEmpty())
            throw new ResourceNotFoundException(DELIVERY_ORDERS_NOT_EXIST);
        fields.forEach((k,v) -> {
            if(k.equals("supplier") || k.equals("customer") || k.equals("deliveryOrder")){
                throw new BadRequestException(OBJECT_IN_BODY);
            }
            else{
                Field field = ReflectionUtils.findField(DetailsDeliveryOrders.class, (String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, optionalDetailsDeliveryOrders.get(), v);
            }
        });
        optionalDetailsDeliveryOrders.get().setModifyDate(new Timestamp(System.currentTimeMillis()));
        detailService.saveDetails(optionalDetailsDeliveryOrders.get());
        return new ResponseEntity<>(
                id, id == null ?
                HttpStatus.NOT_FOUND : id == 0 ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDetailsDeliveryOrders(@PathVariable Long id) {
        Optional<DetailsDeliveryOrders> optionalDeliveryOrders = detailsDeliveryOrderRepository.findById(id);
        if (optionalDeliveryOrders.isEmpty()) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        }
        detailService.delete(optionalDeliveryOrders.get());
        return new ResponseEntity<>(
                optionalDeliveryOrders.get().getId(), optionalDeliveryOrders.isEmpty() ?
                HttpStatus.NOT_FOUND :  HttpStatus.OK
        );
    }
    // TODO findDetailsOrderByItemCode
}

