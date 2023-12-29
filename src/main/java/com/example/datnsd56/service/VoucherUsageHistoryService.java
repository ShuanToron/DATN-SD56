package com.example.datnsd56.service;

import com.example.datnsd56.entity.VoucherUsageHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherUsageHistoryService {
    List<VoucherUsageHistory> findAllOrderByUsedDateDesc();
    Page<VoucherUsageHistory> getall(Pageable pageable);

}
