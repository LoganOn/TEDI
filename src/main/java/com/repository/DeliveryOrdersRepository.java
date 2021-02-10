package com.repository;

import com.handler.DeliveryOrdersDTO;
import com.model.DeliveryOrders;
import com.model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrdersRepository extends CrudRepository<DeliveryOrders, Long> {

    List<DeliveryOrders> findAllBySupplier (Users supplier, Pageable pageable);

    List<DeliveryOrders> findAllByCustomer(Users customer, Pageable pageable);

    Optional<DeliveryOrders> findByBaseRef (String baseRef);

    List<DeliveryOrders> findByBaseRefContaining (String baseRef);

    List<DeliveryOrders> findAll();

//    @Query(value = "SELECT d.deliveryOrderId, d.baseRef, d.numberOrderCustomer, d.docStatus, c.name, d.docTotal, d.docNet, d.docVatSum, d.creationDate FROM DeliveryOrders d " +
//            "JOIN Users s ON s.userId = d.userId1 " +
//            "JOIN Users c on c.userId = d.userId2 " +
//            "WHERE d.userId1 = :supplier", nativeQuery = true
//    )
//    List<DeliveryOrdersDTO> findAllBySupplier(@Param("supplier") Long supplier) ;
//
//    @Query(value = "SELECT d.deliveryOrderId, d.baseRef, d.numberOrderCustomer, d.docStatus, s.name, d.docTotal, d.docNet, d.docVatSum, d.creationDate FROM DeliveryOrders d " +
//            "JOIN Users s ON s.userId = d.userId1 " +
//            "JOIN Users c on c.userId = d.userId2 " +
//            "WHERE d.userId2 = :customer"
//    )
//    List<DeliveryOrdersDTO> findAllByCustomer(@Param("customer") Long customer) ;
}
