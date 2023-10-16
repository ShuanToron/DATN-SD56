package com.example.datnsd56.service;

import com.example.datnsd56.entity.Color;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ColorService {
    Page<Color> getAll(Integer page);

    void add(Color color);

    Color getById(Integer id);

    void delete(Integer id);

    void update(Color color);
}
