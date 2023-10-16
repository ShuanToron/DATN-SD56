package com.example.datnsd56.service;

import com.example.datnsd56.model.Brand;
import com.example.datnsd56.model.Category;
import org.springframework.data.domain.Page;

public interface BrandService {
    Page<Brand> getAll(Integer page);

    void add(Brand brand);

    Brand getById(Integer id);

    void delete(Integer id);

    void update(Brand brand);
}
