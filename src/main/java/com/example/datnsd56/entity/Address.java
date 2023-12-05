package com.example.datnsd56.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
@NotBlank(message = "không được để trống !")
    @Column(name = "streetName")
    private String streetName;
    @NotBlank(message = "không được để trống !")

    @Column(name = "district")
    private String district;


    @NotBlank(message = "không được để trống !")

    @Column(name = "province")
    private String province;
    @NotBlank(message = "không được để trống !")

    @Column(name = "zipcode")
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}
