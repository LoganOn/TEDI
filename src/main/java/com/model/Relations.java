package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Relations {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "RelationId")
  private Long relationId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "SupplierId")
  private Suppliers supplier;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "CustomerId")
  private Customers customer;

  @Column(name = "Active")
  private Boolean active;

  @Column(name = "CreationDate",  nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "ModifyDate",  nullable = false)
  private Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
}
