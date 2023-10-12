package com.example.datnsd56.service;

import com.example.datnsd56.model.Material;
import com.example.datnsd56.model.Size;
import org.springframework.data.domain.Page;

public interface SizeService {
    Page<Size> getAll(Integer page);

    void add(Size size );

    Size getById(Integer id);

    void delete(Integer id);

    void update(Size size);
}
