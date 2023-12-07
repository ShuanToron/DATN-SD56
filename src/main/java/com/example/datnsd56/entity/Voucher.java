package com.example.datnsd56.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
@NotBlank(message = "no de trong")
    @Column(name = "Code")
    private String code;
    @NotBlank(message = "no de trong")
    @Column(name = "Description")
    private String description;
    @Column(name = "StartDate")
    private LocalDateTime startDate;
    @Column(name = "ExpiryDate")
    private LocalDateTime expiryDateTime;
    @Column(name = "Discount")
    private double discount;
    @Column(name = "Active")
    private boolean active;
    @Column(name = "DiscountType")
    private DiscountType discountType;
    // Constructors, getters, setters
}
