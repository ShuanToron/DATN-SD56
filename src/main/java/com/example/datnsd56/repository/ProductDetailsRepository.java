package com.example.datnsd56.repository;

import com.example.datnsd56.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Integer> {
}
