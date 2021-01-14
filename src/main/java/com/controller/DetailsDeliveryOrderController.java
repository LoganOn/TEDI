package com.controller;

import com.model.DetailsDeliveryOrders;
import com.repository.DetailsDeliveryOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/details", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DetailsDeliveryOrderController {

    @Autowired
    private DetailsDeliveryOrderRepository detailsDeliveryOrderRepository;

//    @GetMapping("/all")
//    @ResponseBody
//    public ResponseEntity<?> findAllDetailsOrders() {
//        List<DetailsDeliveryOrders> detailsDeliveryOrders = (List<DetailsDeliveryOrders>) detailsDeliveryOrderRepository.findAll();
//        System.out.println(detailsDeliveryOrders.get(0).toString());
//        return new ResponseEntity<>(
//                detailsDeliveryOrders, detailsDeliveryOrders == null ?
//                HttpStatus.NOT_FOUND : detailsDeliveryOrders.isEmpty() ?
//                HttpStatus.NO_CONTENT : HttpStatus.OK
//        );
//    }
    @GetMapping("/all")
    @ResponseBody
    public List<DetailsDeliveryOrders> findAllDetailsOrders() {
        List<DetailsDeliveryOrders> detailsDeliveryOrders = (List<DetailsDeliveryOrders>) detailsDeliveryOrderRepository.findAll();
        System.out.println(detailsDeliveryOrders.get(0).toString());
        return  detailsDeliveryOrders;
    }
}

