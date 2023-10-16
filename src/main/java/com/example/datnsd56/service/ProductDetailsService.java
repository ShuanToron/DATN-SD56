package com.example.datnsd56.service;

import com.example.datnsd56.entity.ProductDetails;
import org.springframework.data.domain.Page;

public interface ProductDetailsService {
    Page<ProductDetails> getAll(Integer page);

    void add(ProductDetails productDetails );

    ProductDetails getById(Integer id);

    void delete(Integer id);

    void update(ProductDetails productDetails);
}
