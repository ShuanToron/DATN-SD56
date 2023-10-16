package com.example.datnsd56.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;



    @Column(name = "code")
    private String code;



    @Column(name = "name")
    private String name;



    @Column(name = "status")
    private boolean status;



}
