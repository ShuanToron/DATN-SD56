package com.example.datnsd56.service;

import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.entity.VoucherUsage;

public interface VoucherUsageService {
    void save(VoucherUsage voucherUsage);
    void updateVoucherStatusUsage( VoucherUsage voucherUsage);
}
