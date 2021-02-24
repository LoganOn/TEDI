package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.handler.BasicDetailsDeliveryOrderDTO;
import com.handler.DeliveryOrdersDTO;
import com.handler.DetailsDeliveryOrderDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

  @OneToMany(mappedBy = "detailsDeliveryOrders", orphanRemoval = true)
  @JsonBackReference
  private List<Subscriptions> subscriptions;

  @OneToMany(mappedBy = "detailsDeliveryOrders", orphanRemoval = true)
  @JsonBackReference
  private List<Notifications> notifications;

  private String type;

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

  public static DetailsDeliveryOrders toDetailsDeliveryOrders(BasicDetailsDeliveryOrderDTO details) {
    return DetailsDeliveryOrders.builder()
            .itemCode(details.getItemCode())
            .itemName(details.getItemName())
            .quantity(details.getQuantity())
            .codeBars(details.getCodeBars())
            .price(details.getPrice())
            .currency(details.getCurrency())
            .lineTotal(details.getValueTotal())
            .lineNet(details.getValueNet())
            .lineVat(details.getValueVat())
            .discountPrcnt(details.getDiscountPercent())
            .vatPrcnt(details.getVatPercent())
            .scheduledShipDate(details.getScheduledShipDate())
            .build();
  }

  public void updateDetailsDeliveryOrders(DeliveryOrders deliveryOrders, DeliveryOrdersDTO.DetailsDeliveryOrdersList details, Users customer, Users supplier){
    this.deliveryOrder = deliveryOrders;
    this.baseRef = deliveryOrders.getBaseRef();
    this.customer = customer;
    this.supplier = supplier;
    this.itemCode = details.getItemCode();
    this.itemName = details.getItemName();
    this.quantity = details.getQuantity();
    this.codeBars = details.getCodeBars();
    this.price = details.getPrice();
    this.currency = details.getCurrency();
    this.lineTotal = details.getValueTotal();
    this.lineNet = details.getValueNet();
    this.lineVat = details.getValueVat();
    this.discountPrcnt = details.getDiscountPercent();
    this.vatPrcnt = details.getVatPercent();
    this.scheduledShipDate = details.getScheduledShipDate();
    this.active = details.getActive();
    this.onTheWay = details.getOnTheWay();
    this.modifyDate = new Timestamp(System.currentTimeMillis());
  }
}
