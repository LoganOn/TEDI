package com.controller;

import com.model.DeliveryOrders;
import com.model.DetailsDeliveryOrders;
import com.repository.DetailsDeliveryOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/details", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DetailsDeliveryOrderController {

    @Autowired
    private DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;

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
        List<DetailsDeliveryOrders> detailsDeliveryOrder = detailsDeliveryOrderRepository.findAllDetailsDeliveryOrdersByDeliveryOrderId(id);
        return new ResponseEntity<>(
                detailsDeliveryOrder, detailsDeliveryOrder == null ?
                HttpStatus.NOT_FOUND : detailsDeliveryOrder.isEmpty() ?
                HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    // TODO findDetailsOrderByItemCode
}

