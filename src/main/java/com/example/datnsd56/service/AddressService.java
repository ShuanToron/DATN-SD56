package com.example.datnsd56.service;

import com.example.datnsd56.entity.Address;
import org.springframework.data.domain.Page;

public interface AddressService {
    Page<Address> getAll(Integer page);
    Address detail(Integer id);
    void add(Address address);
    void update(Address address);
    void delete(Integer id);

}
