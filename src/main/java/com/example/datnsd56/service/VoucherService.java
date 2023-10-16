package com.example.datnsd56.service;

import com.example.datnsd56.entity.Voucher;
import org.springframework.data.domain.Page;

public interface VoucherService {
    Page<Voucher> getAll(Integer page);

    Voucher detail(Integer id);
    void add(Voucher voucher);
    void update(Voucher voucher);
    void delete(Integer id);
}
