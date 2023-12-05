package com.example.datnsd56.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orderss")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "Shipping_fee")
    private BigDecimal shippingFee;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "address")
    private String address;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "sale_method")
    private String saleMethod;

    @Column(name = "order_status")
    private String orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orders")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private Voucher voucherId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customers customerId;

    public String getStatusName(){
        if (this.orderStatus == "10"){
            return "Chờ xác nhận";
        } else if (this.orderStatus == "3"){
            return "Đã xác nhận";
        } else if (this.orderStatus == "2"){
            return "Đang giao hàng";
        } else if (this.orderStatus == "1"){
            return "Đã hoàn thành";
        } else if (this.orderStatus == "0"){
            return "Đã huỷ";
        } else {
            return null;
        }
    }

}
