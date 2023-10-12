package com.example.datnsd56.service.impl;

import com.example.datnsd56.model.Category;
import com.example.datnsd56.repository.CategoryRepository;
import com.example.datnsd56.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return categoryRepository.findAll(pageable);
    }

    @Override
    public void add(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void update(Category category) {
        categoryRepository.save(category);
    }
}
