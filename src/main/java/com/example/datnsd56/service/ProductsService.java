package com.example.datnsd56.service;

import com.example.datnsd56.entity.Color;
import com.example.datnsd56.entity.Material;
import com.example.datnsd56.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductsService {
    Page<Products> getAll(Integer pageNo);
    List<Products> getAllPro();
    void add(Products products);

    Products getById(Integer id);

    void delete(Integer id);

    void update(Products products);
}
