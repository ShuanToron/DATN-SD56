package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Account;
import com.example.datnsd56.repository.AccountRepository;
import com.example.datnsd56.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountSeviceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Page<Account> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account detail(Integer id) {
        Account account = accountRepository.findById(id).get();
        return account;
    }

    @Override
    public void add(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void update(Account account) {
        accountRepository.save(account);

    }

    @Override
    public void delete(Integer id) {
        Account account = detail(id);
        accountRepository.delete(account);
    }
}
