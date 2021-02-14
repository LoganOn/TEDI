package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class RelationsUsers {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "RelationUsersId")
  private Long relationUsersId;

  @ManyToOne
  @JoinColumn(name = "UserSupplierId")
  @JsonManagedReference
  private Users supplier;

  @ManyToOne
  @JoinColumn(name = "UserCustomerId")
  @JsonManagedReference
  private Users customer;

  @Column(name = "Active")
  private Boolean active;

  @Column(name = "CreationDate", nullable = false)
  private Timestamp creationDate;

  @Column(name = "ModifyDate", nullable = false)
  private Timestamp modifyDate;

  public RelationsUsers(Users supplier, Users customer, boolean active) {
    this.supplier = supplier;
    this.customer = customer;
    this.active = active;
    this.creationDate = new Timestamp(System.currentTimeMillis());
    this.modifyDate = new Timestamp(System.currentTimeMillis());
  }
}
