package com.example.datnsd56.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;



    @Column(name = "status")
    private String status;



    @Column(name = "create_date")
    private Date createDate;


    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountByAccountId;

}
