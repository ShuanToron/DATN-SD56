package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Address;
import com.example.datnsd56.repository.AddressRepository;
import com.example.datnsd56.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressSeviceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Page<Address> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return addressRepository.findAll(pageable);
    }

    @Override
    public Address detail(Integer id) {
        Address address = addressRepository.findById(id).get();
        return address;
    }

    @Override
    public void add(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void update(Address address) {
        addressRepository.save(address);

    }

    @Override
    public void delete(Integer id) {
        Address address = detail(id);
        addressRepository.delete(address);
    }
}
