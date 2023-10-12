package com.example.datnsd56.service;

import com.example.datnsd56.model.Color;
import com.example.datnsd56.model.Material;
import org.springframework.data.domain.Page;

public interface MaterialService {
    Page<Material> getAll(Integer page);

    void add(Material material );

    Material getById(Integer id);

    void delete(Integer id);

    void update(Material material);
}
