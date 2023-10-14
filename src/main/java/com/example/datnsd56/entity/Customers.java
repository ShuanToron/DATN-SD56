package com.example.datnsd56.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "Customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customers {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

}
