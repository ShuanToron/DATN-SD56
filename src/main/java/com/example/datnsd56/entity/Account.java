package com.example.datnsd56.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

@ToString
@Builder
@Entity
@Table(name = "account")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


//    @Column(name = "username")
//    private String username;

    @NotBlank(message = "Không đuộc để trống!")
    @Column(name = "passwords")
    private String password;

    @NotBlank(message = "Không đuộc để trống!")
    @Column(name = "fullname")
    private String name;


    @Column(name = "status")
    private Boolean statuss;


    @Column(name = "create_date")
    private Date createDate;


    @Column(name = "update_date")
    private Date updateDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id ",referencedColumnName = "id")
//    private Roles role_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Roles role_id;


    @NotBlank(message = "Không đuộc để trống!")

    @Column(name = "email")
    private String email;
    @NotBlank(message = "Không đuộc để trống!")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số")

    @Column(name = "phone")
    private String phone;
    @Column(name = "gender")
    private Boolean gender;
    @Past(message ="ngày sinh không hợp lệ! ")
    @NotNull(message = "Không đuộc để trống!")
    @Column(name = "birthdate")
    private LocalDate birthdate;

    @OneToOne(mappedBy = "accountId", cascade = CascadeType.ALL)
    private Cart cart;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  private List<Address> address;
    public Account(){
        this.cart = new Cart();
    }



}
