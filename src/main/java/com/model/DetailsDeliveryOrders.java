package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.handler.BasicDetailsDeliveryOrderDTO;
import com.handler.DetailsDeliveryOrderDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class DetailsDeliveryOrders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "DeliveryOrderId")
  @JsonBackReference
  private DeliveryOrders deliveryOrder;

  @Column(name = "BaseRef", length = 64)
  private String baseRef;

  @ManyToOne
  @JoinColumn(name = "UserSupplierId")
  @JsonManagedReference
  private Users supplier;

  @ManyToOne
  @JoinColumn(name = "UserCustomerId")
  @JsonManagedReference
  private Users customer;

  @Column(name = "ItemCode", length = 100)
  private String itemCode;

  @Column(name = "ItemName")
  private String itemName;

  @Column(name = "Quantity")
  private double quantity;

  @Column(name = "CodeBars", length = 13)
  private String codeBars;

  @Column(name = "Price")
  private double price;

  @Column(name = "Currency", length = 10)
  private String currency;

  @Column(name = "LineTotal")
  private double lineTotal;

  @Column(name = "LineNet")
  private double lineNet;

  @Column(name = "LineVat")
  private double lineVat;

  @Column(name = "DiscountPrcnt", length = 10)
  private String discountPrcnt;

  @Column(name = "VatPrcnt") //przemyslec w jakich formacie, najlepiej jako enum
  private double vatPrcnt;

  @Column(name = "VatGroup", length = 10)
  private String vatGroup;

  @Column(name = "Active")
  private Boolean active;

  @Column(name = "OnTheWay")
  private Boolean onTheWay;

  @Column(name = "ScheduledShipDate", length = 64)
  private String scheduledShipDate;

  @Column(name = "Comments")
  private String comments;

  @Column(name = "CreationDate", nullable = false)
  private Timestamp creationDate;

  @Column(name = "ModifyDate", nullable = false)
  private Timestamp modifyDate;

  public void setParametrs(DeliveryOrders deliveryOrders) {
    this.deliveryOrder = deliveryOrders;
    this.baseRef = deliveryOrders.getBaseRef();
    this.supplier = deliveryOrders.getSupplier();
    this.customer = deliveryOrders.getCustomer();
    this.creationDate = new Timestamp(System.currentTimeMillis());
    this.modifyDate = new Timestamp(System.currentTimeMillis());
    this.active = true;
    this.onTheWay = false;
  }

  public static DetailsDeliveryOrders toDetailsDeliveryOrders(BasicDetailsDeliveryOrderDTO deliveryOrdersList) {
    return DetailsDeliveryOrders.builder()
            .itemCode(deliveryOrdersList.getItemCode())
            .itemName(deliveryOrdersList.getItemName())
            .quantity(deliveryOrdersList.getQuantity())
            .codeBars(deliveryOrdersList.getCodeBars())
            .price(deliveryOrdersList.getPrice())
            .currency(deliveryOrdersList.getCurrency())
            .lineTotal(deliveryOrdersList.getValueTotal())
            .lineNet(deliveryOrdersList.getValueNet())
            .lineVat(deliveryOrdersList.getValueVat())
            .discountPrcnt(deliveryOrdersList.getDiscountPercent())
            .vatPrcnt(deliveryOrdersList.getVatPercent())
            .scheduledShipDate(deliveryOrdersList.getScheduledShipDate())
            .build();
  }
}
