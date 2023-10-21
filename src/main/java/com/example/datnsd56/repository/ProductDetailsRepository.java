package com.example.datnsd56.repository;

import com.example.datnsd56.entity.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Integer> {
//@Query("SELECT v FROM ProductDetails v JOIN v.productId s \n" +
//        "WHERE (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))\n" +
//        "  AND (:minTien IS NULL OR v.sellPrice >= :minTien)\n" +
//        "  AND (:maxTien IS NULL OR v.sellPrice <= :maxTien)")
    @Query("SELECT v FROM ProductDetails v JOIN v.productId s\n" +
            "WHERE v.quantity = ?1 or v.sellPrice= ?1")
Page<ProductDetails> search(@Param("quantity") Integer quantity,
                            @Param("sellPrice") Integer sellPrice,
                            Pageable pageable);
}
