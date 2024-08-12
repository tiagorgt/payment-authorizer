package com.tiagorgt.paymentauthorizer.controller;

import com.tiagorgt.paymentauthorizer.dto.AccountDTO;
import com.tiagorgt.paymentauthorizer.entity.Account;
import com.tiagorgt.paymentauthorizer.service.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testAddAccount() {
        AccountDTO accountDTO = new AccountDTO();
        Account account = new Account();
        when(accountService.addAccount(any(AccountDTO.class))).thenReturn(account);

        ResponseEntity<Account> response = accountController.addAccount(accountDTO);

        assertEquals(ResponseEntity.ok(account), response);
    }

    @Test
    public void testGetAllAccounts() {
        Account account1 = new Account();
        Account account2 = new Account();
        List<Account> accounts = Arrays.asList(account1, account2);
        when(accountService.getAllAccounts()).thenReturn(accounts);

        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        assertEquals(ResponseEntity.ok(accounts), response);
    }

    @Test
    public void testGetAccountById_Found() {
        Account account = new Account();
        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        ResponseEntity<Account> response = accountController.getAccountById(1L);

        assertEquals(ResponseEntity.ok(account), response);
    }

    @Test
    public void testGetAccountById_NotFound() {
        when(accountService.getAccountById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Account> response = accountController.getAccountById(1L);

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}