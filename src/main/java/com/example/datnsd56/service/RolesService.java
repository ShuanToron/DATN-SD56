package com.example.datnsd56.service;

import com.example.datnsd56.entity.Roles;
import org.springframework.data.domain.Page;

public interface RolesService {
    Page<Roles> getAll(Integer page);
    Roles detail(Integer id);
    void add(Roles roles);
    void update(Roles roles);
    void delete(Integer id);

}
