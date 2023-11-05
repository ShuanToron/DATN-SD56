package com.example.datnsd56.repository;

import com.example.datnsd56.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Integer> {
    @Query(value = "select * from Products where id=?1", nativeQuery = true)
//    List<ProductDetails> getAllDetail(Integer id);
    List<Products> getProductDetailsById(Integer id);
}
