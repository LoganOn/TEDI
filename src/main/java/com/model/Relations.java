package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Relations {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "RelationId")
  private Long relationId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "SupplierId")
  @JsonBackReference
  private Suppliers supplier;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "CustomerId")
  @JsonBackReference
  private Customers customer;

  @Column(name = "Active")
  private Boolean active;

  @Column(name = "CreationDate",  nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "ModifyDate",  nullable = false)
  private Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
}
