package com.example.datnsd56.service;

import com.example.datnsd56.entity.Voucher;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VoucherService {
    Page<Voucher> getAll(Integer page);
List<Voucher> get();
    Voucher detail(Integer id);
    void add(Voucher voucher);
    void update(Voucher voucher);
    void delete(Integer id);
}
