package com.example.datnsd56.service;

import com.example.datnsd56.entity.Material;
import com.example.datnsd56.entity.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductDetailsService {
    Page<ProductDetails> getAll(Integer pageNo);

    List<ProductDetails> getAllCTSP();

    ProductDetails add(ProductDetails productDetails );

    ProductDetails getById(Integer id);

    void delete(Integer id);

    void update(ProductDetails productDetails);
}
