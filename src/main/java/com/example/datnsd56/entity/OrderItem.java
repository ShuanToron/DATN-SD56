//package com.example.datnsd56.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "order_item")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class OrderItem {
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "price")
//    private BigDecimal price;
//
//    @Column(name = "quantity")
//    private Integer quantity;
//
//    @Column(name = "status")
//    private String status;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
//    private Orders orders;
//
//    @Column(name = "rate_id")
//    private Integer rateId;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_details_id", referencedColumnName = "id")
//    private ProductDetails productDetails;
//
//
//}
