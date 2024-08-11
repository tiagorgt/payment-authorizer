package com.tiagorgt.paymentauthorizer.service;

import com.tiagorgt.paymentauthorizer.dto.AccountDTO;
import com.tiagorgt.paymentauthorizer.entity.Account;
import com.tiagorgt.paymentauthorizer.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCashBalance(BigDecimal.valueOf(100));
        accountDTO.setFoodBalance(BigDecimal.valueOf(50));
        accountDTO.setMealBalance(BigDecimal.valueOf(30));

        Account account = new Account();
        account.setCashBalance(BigDecimal.valueOf(100));
        account.setFoodBalance(BigDecimal.valueOf(50));
        account.setMealBalance(BigDecimal.valueOf(30));

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.addAccount(accountDTO);
        assertEquals(BigDecimal.valueOf(100), result.getCashBalance());
        assertEquals(BigDecimal.valueOf(50), result.getFoodBalance());
        assertEquals(BigDecimal.valueOf(30), result.getMealBalance());
    }

    @Test
    public void testGetAccountById() {
        Account account = new Account();
        account.setId(1L);
        account.setCashBalance(BigDecimal.valueOf(100));
        account.setFoodBalance(BigDecimal.valueOf(50));
        account.setMealBalance(BigDecimal.valueOf(30));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<Account> result = accountService.getAccountById(1L);
        assertTrue(result.isPresent());
        assertEquals(BigDecimal.valueOf(100), result.get().getCashBalance());
        assertEquals(BigDecimal.valueOf(50), result.get().getFoodBalance());
        assertEquals(BigDecimal.valueOf(30), result.get().getMealBalance());
    }

    @Test
    public void testSaveAccount() {
        Account account = new Account();
        account.setCashBalance(BigDecimal.valueOf(100));
        account.setFoodBalance(BigDecimal.valueOf(50));
        account.setMealBalance(BigDecimal.valueOf(30));

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.saveAccount(account);
    }

    @Test
    public void testGetAllAccounts() {
        Account account1 = new Account();
        account1.setCashBalance(BigDecimal.valueOf(100));
        account1.setFoodBalance(BigDecimal.valueOf(50));
        account1.setMealBalance(BigDecimal.valueOf(30));

        Account account2 = new Account();
        account2.setCashBalance(BigDecimal.valueOf(200));
        account2.setFoodBalance(BigDecimal.valueOf(100));
        account2.setMealBalance(BigDecimal.valueOf(60));

        List<Account> accounts = Arrays.asList(account1, account2);

        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();
        assertEquals(2, result.size());
        assertEquals(BigDecimal.valueOf(100), result.get(0).getCashBalance());
        assertEquals(BigDecimal.valueOf(200), result.get(1).getCashBalance());
    }
}