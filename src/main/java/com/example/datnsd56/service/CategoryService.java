package com.example.datnsd56.service;

import com.example.datnsd56.model.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<Category> getAll(Integer page);

    void add(Category category);

    Category getById(Integer id);

    void delete(Integer id);

    void update(Category category);
}
