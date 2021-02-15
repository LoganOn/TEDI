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
import com.service.DetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/details", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DetailsDeliveryOrderController {

    private final String DELIVERY_ORDER_NOT_EXIST = "Delivery order not exist";

    private final String RESOURCE_NOT_FOUND = "Resource not found";

    private DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;

    private DeliveryOrdersRepository deliveryOrdersRepository;

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

