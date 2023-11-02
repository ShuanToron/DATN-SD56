package com.example.datnsd56.repository;

import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Integer> {
    //@Query("SELECT v FROM ProductDetails v JOIN v.productId s \n" +
//        "WHERE (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))\n" +
//        "  AND (:minTien IS NULL OR v.sellPrice >= :minTien)\n" +
//        "  AND (:maxTien IS NULL OR v.sellPrice <= :maxTien)")
    @Transactional
    @Modifying
    @Query(value = "update [Product_details] set status = 0 where id = ?",nativeQuery = true)
    void deletects(@Param("id") Integer id);

    @Query(value = "SELECT v.id,v.color_id,v.size_id,v.quantity,v.sell_price,v.create_date,v.update_date,v.product_id,v.status FROM Product_details v JOIN Products s ON v.product_id = s.id  \n" +
            "            WHERE v.quantity = ? or v.sell_price= ?",nativeQuery = true)
//    @Query("SELECT v FROM ProductDetails v JOIN v.productId s\n" +
//            "WHERE v.quantity = ?1 or v.sellPrice= ?1")
    Page<ProductDetails> search(@Param("quantity") Integer quantity,
                                @Param("sellPrice") BigDecimal sellPrice,
                                Pageable pageable);

//    List<ProductDetails> findByProductId(Integer productId);

}


