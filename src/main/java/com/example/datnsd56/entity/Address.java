package com.example.datnsd56.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "streetName")
    private String streetName;

    @Column(name = "district")
    private String district;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "zipcode")
    private String zipcode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountId;

}
