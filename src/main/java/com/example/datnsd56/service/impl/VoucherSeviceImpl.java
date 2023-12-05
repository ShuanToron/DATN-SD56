package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.repository.VoucherRepository;
import com.example.datnsd56.service.VoucherService;
import jakarta.transaction.Transactional;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherSeviceImpl  {
    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher getVoucherById(Integer id) {
        return voucherRepository.findById(id).orElse(null);
    }

    public void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }
    public void updateVoucher(Voucher voucher) {
        Optional<Voucher> existingVoucherOptional = voucherRepository.findById(voucher.getId());

        if (existingVoucherOptional.isPresent()) {
            Voucher existingVoucher = existingVoucherOptional.get();

            // Cập nhật chỉ những trường mong muốn
            existingVoucher.setCode(voucher.getCode());
            existingVoucher.setActive(true);
            existingVoucher.setDescription(voucher.getDescription());
            existingVoucher.setExpiryDateTime(voucher.getExpiryDateTime());
            existingVoucher.setDiscount(voucher.getDiscount());
            existingVoucher.setDiscountType(voucher.getDiscountType());

            voucherRepository.save(existingVoucher);
        }
    }

    public void deleteVoucher(Integer id) {
        voucherRepository.deleteById(id);
    }

    @Transactional
    public void checkAndDeactivateExpiredVouchers() {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            List<Voucher> expiredVouchers = voucherRepository.findByExpiryDateTimeBeforeAndActiveIsTrue(currentDateTime);

            for (Voucher voucher : expiredVouchers) {
                voucher.setActive(false);
            }
            voucherRepository.saveAll(expiredVouchers);
        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
        }
        }
}
