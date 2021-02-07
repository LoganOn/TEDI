package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.handler.DeliveryOrdersDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryOrders {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "DeliveryOrderId")
  private Long deliveryOrderId;

  @Column(name = "BaseRef", length = 64)
  private String baseRef;

  @Column(name = "NumberOrderCustomer", length = 64)
  private String numberOrderCustomer;

  //Close, open, cancelled
  @Column(name = "DocStatus", length = 1, nullable = false)
  private char docStatus;

  @ManyToOne
  @JoinColumn(name = "UserSupplierId")
  @JsonManagedReference
  private Users supplier;

  @ManyToOne
  @JoinColumn(name = "UserCustomerId")
  @JsonManagedReference
  private Users customer;

  @Column(name = "DocTotal")
  private double docTotal;

  @Column(name = "DocNet")
  private double docNet;

  @Column(name = "DocVatSum")
  private double docVatSum;

  @Column(name = "Description")
  private String description;

  @Column(name = "CreationDate",  nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "ModifyDate",  nullable = false)
  private Timestamp modifyDate = new Timestamp(System.currentTimeMillis());

  @OneToMany(mappedBy = "deliveryOrder", orphanRemoval = true)
  @JsonBackReference
  private List<DetailsDeliveryOrders> detailsDeliveryOrdersList;

  public static DeliveryOrders toDeliveryOrders(DeliveryOrdersDTO deliveryOrdersDTO) {
    return DeliveryOrders.builder()
            .baseRef(deliveryOrdersDTO.getBaseRef())
            .numberOrderCustomer(deliveryOrdersDTO.getNumberOrderCustomer())
            .docStatus(deliveryOrdersDTO.getDocStatus())
            .docTotal(deliveryOrdersDTO.getDocTotal())
            .docNet(deliveryOrdersDTO.getDocNet())
            .docVatSum(deliveryOrdersDTO.getDocVatSum())
            .description(deliveryOrdersDTO.getDescription())
            .build();
  }

}
