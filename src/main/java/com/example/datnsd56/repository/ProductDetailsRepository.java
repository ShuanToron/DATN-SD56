package com.example.datnsd56.repository;

import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Integer> {
    //@Query("SELECT v FROM ProductDetails v JOIN v.productId s \n" +
//        "WHERE (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))\n" +
//        "  AND (:minTien IS NULL OR v.sellPrice >= :minTien)\n" +
//        "  AND (:maxTien IS NULL OR v.sellPrice <= :maxTien)")
    @Transactional
    @Modifying
    @Query(value = "update [Product_details] set status = 0 where id = ?", nativeQuery = true)
    void deletects(@Param("id") Integer id);

    @Query(value = "SELECT v.id,v.color_id,v.size_id,v.quantity,v.sell_price,v.create_date,v.update_date,v.product_id,v.status FROM Product_details v JOIN Products s ON v.product_id = s.id  \n" +
        "            WHERE v.quantity = ?1 or v.sell_price= ?1 or v.id = ?1", nativeQuery = true)
//    @Query("SELECT v FROM ProductDetails v JOIN v.productId s\n" +
//            "WHERE v.quantity = ?1 or v.sellPrice= ?1")
    Page<ProductDetails> findProductDetailsBySellPrice(BigDecimal sellPrice, Pageable pageable);

//    List<ProductDetails> findByProductId(Integer productId);


    @Query(value = "select * from Product_details where status = 0", nativeQuery = true)
    List<ProductDetails> listPending();

    @Query(value = "select * from Product_details where product_id=?1", nativeQuery = true)
    List<ProductDetails> getAllDetail(Integer id);
    @Query(value = "select * from Product_details where id=?1", nativeQuery = true)
    List<ProductDetails> getProductDetailsByProductId(Integer id);
//    @Query(value = "select * fgetrom Product_details where product_id=?1", nativeQuery = true)
////    List<ProductDetails> getAllDetail(Integer id);
//   List< Products> findProductDetailsByProductId(Integer id);
@Query(value = "select sell_price from Product_details where product_id=?1 and color_id=?2 and size_id=?3  ", nativeQuery = true)
BigDecimal getDetail(Integer productId,Integer color, Integer size);
    @Query(value = "select * from Product_details where   color_id=?1 and size_id=?1  ", nativeQuery = true)
    @Min(value = 1, message = "lon hon 0") BigDecimal getPrice(String color, String size);

    @Query(value = "select p.sell_price ,p.id,p.color_id,p.create_date,p.product_id,p.quantity,p.size_id,p.status,p.update_date from Product_details AS p join Color as c on p.color_id=c.id join Size as s on s.id=p.size_id join Products as pr on pr.id=p.product_id  where  p.product_id=?1 ",nativeQuery = true)
    List<ProductDetails> getProductDetailsById(Integer id);
@Query(value = "select p.sell_price from Product_details AS p where color_id=?1 and size_id=?1 and id=?1 ",nativeQuery = true)
    List<ProductDetails> findProductDetailsByColorIdAndSizeIdAndAndProductId(Integer colorId,Integer sizeId);
//}
}
