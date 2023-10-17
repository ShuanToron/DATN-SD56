package com.example.datnsd56.service;

import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductsService {
    Page<Products> getAll(Integer page);

    List<Products> getAllSP();

    void add(Products products );

    Products getById(Integer id);

    void delete(Integer id);

    void update(Products products);
}
