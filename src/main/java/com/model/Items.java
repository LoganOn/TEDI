package com.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Items {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ItemId")
  private Long itemId;

  @Column(name = "ItemCode", length = 100)
  private String itemCode;

  @Column(name = "ItemName")
  private String itemName;

  @Column(name = "CodeBars", length = 13)
  private String codeBars;

  @Column(name = "Price")
  private double price;

  @Column(name = "Currency", length = 10)
  private String currency;

  @Column(name = "VatPrcnt")
  private double vatPrcnt;

  @Column(name = "VatGroup", length = 10)
  private String vatGroup;

  @Column(name = "Active")
  private boolean active;

  @Column(name = "Availability")
  private String availability;
}
