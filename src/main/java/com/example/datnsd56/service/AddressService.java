package com.example.datnsd56.service;

import com.example.datnsd56.entity.Address;
import com.example.datnsd56.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    Page<Address> getAll(Pageable pageable);
    Address detail(Integer id);
    Address add(Address address);
    void update(Address address);
    void delete(Integer id);
    Page<Address> findByEmail(String phone);
    List<Address> get();

}
