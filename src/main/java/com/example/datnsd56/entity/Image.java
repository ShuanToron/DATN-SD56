package com.example.datnsd56.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Table(name = "Image")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Column(name = "url")
    private Blob url;
<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "productDetail_id",referencedColumnName = "id")
    private ProductDetails productDetails;
=======
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Products productId;
>>>>>>> 5bee6bb0a3605b2017ce2dbf69b83048f5a89b1b
}
