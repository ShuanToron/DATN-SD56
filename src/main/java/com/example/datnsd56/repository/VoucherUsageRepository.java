package com.example.datnsd56.repository;

import com.example.datnsd56.entity.Account;
import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.entity.VoucherUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherUsageRepository extends JpaRepository<VoucherUsage,Integer> {
    Optional<VoucherUsage> findTopByAccountIdAndIsUsedFalseAndVoucher_ActiveTrueOrderByUsedDateDesc(Integer accountId);
    Optional<VoucherUsage> findByAccountAndVoucherAndIsUsedTrue(Account account, Voucher voucher);
}
