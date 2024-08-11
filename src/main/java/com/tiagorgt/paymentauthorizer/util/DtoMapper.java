package com.tiagorgt.paymentauthorizer.util;

import com.tiagorgt.paymentauthorizer.dto.AccountDTO;
import com.tiagorgt.paymentauthorizer.dto.TransactionDTO;
import com.tiagorgt.paymentauthorizer.entity.Account;
import com.tiagorgt.paymentauthorizer.entity.Transaction;

public class DtoMapper {
    public static Account toAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setCashBalance(accountDTO.getCashBalance());
        account.setFoodBalance(accountDTO.getFoodBalance());
        account.setMealBalance(accountDTO.getMealBalance());
        return account;
    }

    public static TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount(transaction.getId().toString());
        transactionDTO.setTotalAmount(transaction.getAmount());
        transactionDTO.setMerchant(transaction.getMerchant());
        transactionDTO.setMcc(transaction.getMcc());
        return transactionDTO;
    }
}