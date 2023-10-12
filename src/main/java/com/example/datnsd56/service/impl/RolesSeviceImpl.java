package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Roles;
import com.example.datnsd56.repository.RolesRepository;
import com.example.datnsd56.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RolesSeviceImpl implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Page<Roles> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return rolesRepository.findAll(pageable);
    }

    @Override
    public Roles detail(Integer id) {
        Roles roles = rolesRepository.findById(id).get();
        return roles;
    }

    @Override
    public void add(Roles roles) {
        rolesRepository.save(roles);
    }

    @Override
    public void update(Roles roles) {
        rolesRepository.save(roles);

    }

    @Override
    public void delete(Integer id) {
        Roles roles = detail(id);
        rolesRepository.delete(roles);
    }
}
