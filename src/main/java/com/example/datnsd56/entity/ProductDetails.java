package com.example.datnsd56.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Product_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "quantity")
    @NotNull(message = "Không đuộc để trống!")
    private Integer quantity;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "status")
    private boolean status;

    @Column(name = "sell_price")
    @Min(value = 1, message = "lon hon 0")
    private BigDecimal sellPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Products productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Size sizeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color colorId;

    @Transient
    @OneToMany(mappedBy = "productDetail_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;
}
