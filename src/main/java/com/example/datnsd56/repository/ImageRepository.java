package com.example.datnsd56.repository;

import com.example.datnsd56.entity.Image;
import com.example.datnsd56.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "select * from Image where productDetail_id = ?1", nativeQuery = true)
    List<Image> getImageByProductId(Integer productId);
}
