package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.repository.VoucherRepository;
import com.example.datnsd56.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoucherSeviceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public Page<Voucher> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAll(pageable);
    }


    @Override
    public Page<Voucher> getAllbypad(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }

    @Override
    public List<Voucher> get() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher detail(Integer id) {
        Voucher voucher = voucherRepository.findById(id).orElse(null);
        return voucher;
    }

    @Override
    public Voucher add(Voucher voucher) {
        voucher.setStartDate(LocalDate.now());
        voucher.setExpirationDate(LocalDate.now());

        return   voucherRepository.save(voucher);
    }

    @Override
    public void update(Voucher voucher) {
        voucher.setStartDate(LocalDate.now());
        voucher.setExpirationDate(LocalDate.now());


        voucherRepository.save(voucher);

    }

    @Override
    public void delete(Integer id) {
        Voucher voucher = detail(id);
        voucherRepository.delete(voucher);
    }
}
