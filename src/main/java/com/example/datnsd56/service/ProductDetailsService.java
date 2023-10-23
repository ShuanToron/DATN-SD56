package com.example.datnsd56.service;

import com.example.datnsd56.entity.ProductDetails;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDetailsService {
    Page<ProductDetails> getAll(Integer pageNo);

    List<ProductDetails> getAllCTSP();

    Page<ProductDetails> search(Integer quantity, BigDecimal sellPrice);

    ProductDetails add(ProductDetails productDetails );

    ProductDetails getById(Integer id);

    void delete(Integer id);

    void update(ProductDetails productDetails);
}
