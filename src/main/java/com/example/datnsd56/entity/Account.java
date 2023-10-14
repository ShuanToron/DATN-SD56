package com.example.datnsd56.entity;




import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "id")
    private Integer id;



//    @Column(name = "username")
//    private String username;


    @Column(name = "password")
    private String password;


    @Column(name = "fullname")
    private String fullname;


    @Column(name = "status")
    private boolean status;


    @Column(name = "create_date")
    private Date createDate;


    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleid ",referencedColumnName = "id")
    private Roles roles;

    @Column(name = "email")
    private String email;


}
