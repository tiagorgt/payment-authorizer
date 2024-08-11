package com.tiagorgt.paymentauthorizer.service;

import com.tiagorgt.paymentauthorizer.dto.AccountDTO;
import com.tiagorgt.paymentauthorizer.entity.Account;
import com.tiagorgt.paymentauthorizer.repository.AccountRepository;
import com.tiagorgt.paymentauthorizer.util.DtoMapper;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account addAccount(AccountDTO account) {
        return accountRepository.save(DtoMapper.toAccount(account));
    }

    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}