package com.example.datnsd56.service;

import com.example.datnsd56.entity.Brand;
import org.springframework.data.domain.Page;

public interface BrandService {
    Page<Brand> getAll(Integer page);

    void add(Brand brand);

    Brand getById(Integer id);

    void delete(Integer id);

    void update(Brand brand);
}
