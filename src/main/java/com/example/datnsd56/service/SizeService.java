package com.example.datnsd56.service;

import com.example.datnsd56.entity.Size;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SizeService {
    Page<Size> getAll(Integer pageNo);

    List<Size> getAllSZ();

    void add(Size size );

    Size getById(Integer id);

    void delete(Integer id);

    void update(Size size);
}
