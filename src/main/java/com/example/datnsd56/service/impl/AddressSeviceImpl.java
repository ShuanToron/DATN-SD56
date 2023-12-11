package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Account;
import com.example.datnsd56.entity.Address;
import com.example.datnsd56.repository.AddressRepository;
import com.example.datnsd56.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressSeviceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Page<Address> getAll(Pageable pageable) {
//        Pageable pageable = PageRequest.of(page, 5);
        return addressRepository.findAll(pageable);
    }

    @Override
    public Address detail(Integer id) {
        Address address = addressRepository.findById(id).orElse(null);
        return address;
    }
    public Address addNewAddress(Account account, Address newAddress) {
        newAddress.setAccount(account);
        // Lưu newAddress vào cơ sở dữ liệu
        // ...
        return addressRepository.save(newAddress);
    }

    @Override
    public List<Address> findAccountAddresses(Integer accountId) {
        return addressRepository.findAccountAddresses(accountId);
    }

    @Override
    public Address add(Address address) {

            // Lấy thông tin Account từ đối tượng Address
            Account account = address.getAccount();
            // Gán Acount cho đối tượng Address nếu chưa có
            if (account != null) {
                address.setAccount(account);
            }

            // Thực hiện thêm địa chỉ vào cơ sở dữ liệu
            // ...



        return  addressRepository.save(address);
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
    public Page<Address> findByEmail(String phone) {
        Pageable page=PageRequest.of(0,5);
        Page<Address> list=addressRepository.findAddressesByPhone(phone,page);
        return list;
    }

    @Override
    public List<Address> get() {
        return addressRepository.findAll();
    }

    @Override
    public Address findAccountDefaultAddress(Integer AccountId) {
        return addressRepository.findAccountDefaultAddress(AccountId);
    }
}
