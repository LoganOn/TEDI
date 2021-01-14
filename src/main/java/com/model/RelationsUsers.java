package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "RelationUsersId")
  private Long relationUsersId;

  @Column(name = "UserId1")
  private Long userId1;

  @Column(name = "UserId2")
  private Long userId2;

  @Column(name = "Active")
  private Boolean active;

  @Column(name = "CreationDate",  nullable = false)
  private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

  @Column(name = "ModifyDate",  nullable = false)
  private Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
}
