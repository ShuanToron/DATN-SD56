package com.example.datnsd56.service;

import com.example.datnsd56.model.Material;
import com.example.datnsd56.model.Products;
import org.springframework.data.domain.Page;

public interface ProductsService {
    Page<Products> getAll(Integer page);

    void add(Products products );

    Products getById(Integer id);

    void delete(Integer id);

    void update(Products products);
}
