package com.tiagorgt.paymentauthorizer.controller;

import com.tiagorgt.paymentauthorizer.dto.AccountDTO;
import com.tiagorgt.paymentauthorizer.entity.Account;
import com.tiagorgt.paymentauthorizer.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> addAccount(@Valid @RequestBody AccountDTO account) {
        Account response = accountService.addAccount(account);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}