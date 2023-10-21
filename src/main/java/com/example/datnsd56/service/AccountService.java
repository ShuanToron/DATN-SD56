package com.example.datnsd56.service;

import com.example.datnsd56.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    Page<Account> getAll(Pageable pageable);
    Account detail(Integer id);
    Account add(Account account);
    void update(Account account);
    void delete(Integer id);
    Page<Account> findByEmail(String phone);
    List<Account> get();

}
