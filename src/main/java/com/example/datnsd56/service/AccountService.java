package com.example.datnsd56.service;

import com.example.datnsd56.entity.Account;
import org.springframework.data.domain.Page;

public interface AccountService {
    Page<Account> getAll(Integer page);
    Account detail(Integer id);
    void add(Account account);
    void update(Account account);
    void delete(Integer id);

}
