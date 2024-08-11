package com.tiagorgt.paymentauthorizer.util;

import com.tiagorgt.paymentauthorizer.dto.AccountDTO;
import com.tiagorgt.paymentauthorizer.dto.TransactionDTO;
import com.tiagorgt.paymentauthorizer.entity.Account;
import com.tiagorgt.paymentauthorizer.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoMapperTest {

    @Test
    public void testToAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCashBalance(BigDecimal.valueOf(100));
        accountDTO.setFoodBalance(BigDecimal.valueOf(50));
        accountDTO.setMealBalance(BigDecimal.valueOf(30));

        Account account = DtoMapper.toAccount(accountDTO);

        assertEquals(accountDTO.getCashBalance(), account.getCashBalance());
        assertEquals(accountDTO.getFoodBalance(), account.getFoodBalance());
        assertEquals(accountDTO.getMealBalance(), account.getMealBalance());
    }

    @Test
    public void testToTransactionDTO() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setMerchant("PADARIA DO ZE               SAO PAULO BR");
        transaction.setMcc("5411");

        TransactionDTO transactionDTO = DtoMapper.toTransactionDTO(transaction);

        assertEquals(transaction.getId().toString(), transactionDTO.getAccount());
        assertEquals(transaction.getAmount(), transactionDTO.getTotalAmount());
        assertEquals(transaction.getMerchant(), transactionDTO.getMerchant());
        assertEquals(transaction.getMcc(), transactionDTO.getMcc());
    }
}