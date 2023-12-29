package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.VoucherUsageHistory;
import com.example.datnsd56.repository.VoucherUsageHistoryRepository;
import com.example.datnsd56.service.VoucherUsageHistoryService;
import com.example.datnsd56.service.VoucherUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherUsageHistoryServiceImpl implements VoucherUsageHistoryService {
    @Autowired
    private VoucherUsageHistoryRepository voucherUsageHistoryRepository;
    @Override
    public List<VoucherUsageHistory> findAllOrderByUsedDateDesc() {
        return voucherUsageHistoryRepository.findAllOrderByUsedDateDesc();
    }

    @Override
    public Page<VoucherUsageHistory> getall(Pageable pageable) {
        return voucherUsageHistoryRepository.getall(pageable);
    }
}
