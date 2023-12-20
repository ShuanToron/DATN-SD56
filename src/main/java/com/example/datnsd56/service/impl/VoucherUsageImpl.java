package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.VoucherUsage;
import com.example.datnsd56.repository.VoucherUsageRepository;
import com.example.datnsd56.service.VoucherUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherUsageImpl implements VoucherUsageService {
    @Autowired
    private VoucherUsageRepository voucherUsageRepository;
    @Override
    public void save(VoucherUsage voucherUsage) {
        voucherUsageRepository.save(voucherUsage);
    }

    @Override
    public void updateVoucherStatusUsage(VoucherUsage voucherUsage) {
        voucherUsage.setIsUsed(true);
        voucherUsageRepository.save(voucherUsage);
    }
}
