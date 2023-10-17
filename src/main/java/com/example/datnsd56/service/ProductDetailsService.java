package com.example.datnsd56.service;

import com.example.datnsd56.entity.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDetailsService {
    Page<ProductDetails> getAll(Pageable pageable);

    void add(ProductDetails productDetails );

    ProductDetails getById(Integer id);

    void delete(Integer id);

    void update(ProductDetails productDetails);
}
