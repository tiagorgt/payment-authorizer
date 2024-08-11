package com.tiagorgt.paymentauthorizer.service;

import com.tiagorgt.paymentauthorizer.dto.TransactionDTO;
import com.tiagorgt.paymentauthorizer.dto.TransactionResultDTO;
import com.tiagorgt.paymentauthorizer.entity.Account;
import com.tiagorgt.paymentauthorizer.enums.TransactionStatus;
import com.tiagorgt.paymentauthorizer.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthorizeTransaction_AccountNotFound() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount("1");

        when(accountService.getAccountById(1L)).thenReturn(Optional.empty());

        TransactionResultDTO result = transactionService.authorizeTransaction(transactionDTO);
        assertEquals(TransactionStatus.OTHER_PROBLEM.getCode(), result.getCode());
        assertEquals(TransactionStatus.OTHER_PROBLEM.getDescription(), result.getMessage());
    }

    @Test
    public void testAuthorizeTransaction_SufficientFoodBalance() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount("1");
        transactionDTO.setTotalAmount(BigDecimal.valueOf(50));
        transactionDTO.setMerchant("supermarket");

        Account account = new Account();
        account.setFoodBalance(BigDecimal.valueOf(100));
        account.setCashBalance(BigDecimal.valueOf(50));
        account.setMealBalance(BigDecimal.valueOf(30));

        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        TransactionResultDTO result = transactionService.authorizeTransaction(transactionDTO);
        assertEquals(TransactionStatus.APPROVED.getCode(), result.getCode());
        assertEquals(TransactionStatus.APPROVED.getDescription(), result.getMessage());
    }

    
    @Test
    public void testAuthorizeTransaction_SufficientMealBalance() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount("1");
        transactionDTO.setTotalAmount(BigDecimal.valueOf(30));
        transactionDTO.setMerchant("UBER EATS                   SAO PAULO BR");

        Account account = new Account();
        account.setFoodBalance(BigDecimal.valueOf(10));
        account.setCashBalance(BigDecimal.valueOf(10));
        account.setMealBalance(BigDecimal.valueOf(30));

        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        TransactionResultDTO result = transactionService.authorizeTransaction(transactionDTO);
        assertEquals(TransactionStatus.APPROVED.getCode(), result.getCode());
        assertEquals(TransactionStatus.APPROVED.getDescription(), result.getMessage());
    }

    @Test
    public void testAuthorizeTransaction_SufficientCashBalance() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount("1");
        transactionDTO.setTotalAmount(BigDecimal.valueOf(50));
        transactionDTO.setMerchant("other");

        Account account = new Account();
        account.setFoodBalance(BigDecimal.valueOf(10));
        account.setCashBalance(BigDecimal.valueOf(100));
        account.setMealBalance(BigDecimal.valueOf(30));

        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        TransactionResultDTO result = transactionService.authorizeTransaction(transactionDTO);
        assertEquals(TransactionStatus.APPROVED.getCode(), result.getCode());
        assertEquals(TransactionStatus.APPROVED.getDescription(), result.getMessage());
    }

    @Test
    public void testAuthorizeTransaction_InsufficientBalance() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount("1");
        transactionDTO.setTotalAmount(BigDecimal.valueOf(150));
        transactionDTO.setMerchant("other");

        Account account = new Account();
        account.setFoodBalance(BigDecimal.valueOf(10));
        account.setCashBalance(BigDecimal.valueOf(50));
        account.setMealBalance(BigDecimal.valueOf(30));

        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        TransactionResultDTO result = transactionService.authorizeTransaction(transactionDTO);
        assertEquals(TransactionStatus.INSUFFICIENT_BALANCE.getCode(), result.getCode());
        assertEquals(TransactionStatus.INSUFFICIENT_BALANCE.getDescription(), result.getMessage());
    }
}