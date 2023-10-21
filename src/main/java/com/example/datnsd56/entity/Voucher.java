package com.example.datnsd56.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Voucher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
     @NotBlank(message = "không được để trong")
    @Column(name = "code")
    private String code;
    @Min(value = 1,message = "lon hon 0")
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private Boolean status;

    @Min(value = 1,message = "lon hon 0")
    @Column(name = "max_value")
    private BigDecimal maxValue;

    @NotBlank(message = "không được để trong")
    @Column(name = "type")
    private String type;

    @Column(name = "start_date")
    private LocalDate startDate;


    @Column(name = "expiration_date")
    private LocalDate expirationDate;

}
