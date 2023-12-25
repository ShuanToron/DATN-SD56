package com.example.datnsd56.service;

import com.example.datnsd56.entity.Account;
import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.entity.VoucherUsage;

import java.util.List;

public interface VoucherUsageService {
    void save(VoucherUsage voucherUsage);
    void updateVoucherStatusUsage( VoucherUsage voucherUsage);

    void saveVoucherForAccount(Voucher voucher, Account account);
    List<VoucherUsage> findVoucherUsagesByAccount(Integer accountId);

}
