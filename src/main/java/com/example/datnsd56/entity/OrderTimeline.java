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
@Table(name = "orderTimeline")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTimeline {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "order_id")
    private Integer orderId;

}
