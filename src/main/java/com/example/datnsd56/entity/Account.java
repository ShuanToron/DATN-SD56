package com.example.datnsd56.entity;




import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String fullname;


    @Column(name = "status")
    private Boolean status;


    @Column(name = "create_date")
    private Date createDate;


    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id ",referencedColumnName = "id")
    private Roles role_id;
    @NotBlank(message = "Không đuộc để trống!")

    @Column(name = "email")
    private String email;
    @NotBlank(message = "Không đuộc để trống!")
    @Pattern(regexp="\\d{10}", message="Số điện thoại phải có 10 chữ số")

    @Column(name = "phone")
    private String phone;
@Column(name = "gender")
    private Boolean gender;
    @NotNull (message = "Không đuộc để trống!")
@Column(name = "birthdate")
    private LocalDate birthdate;
}
