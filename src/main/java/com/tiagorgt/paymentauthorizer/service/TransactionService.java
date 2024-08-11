package com.tiagorgt.paymentauthorizer.service;

import com.tiagorgt.paymentauthorizer.dto.TransactionDTO;
import com.tiagorgt.paymentauthorizer.dto.TransactionResultDTO;
import com.tiagorgt.paymentauthorizer.entity.Account;
import com.tiagorgt.paymentauthorizer.entity.Transaction;
import com.tiagorgt.paymentauthorizer.enums.TransactionStatus;
import com.tiagorgt.paymentauthorizer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public TransactionResultDTO authorizeTransaction(TransactionDTO transaction) {
        logger.info("Authorizing transaction for account ID: {}", transaction.getAccount());	
        Optional<Account> optionalAccount = accountService.getAccountById(Long.valueOf(transaction.getAccount()));
        if (optionalAccount.isEmpty()) {
            logger.error("Account not found for account ID: {}", transaction.getAccount());
            return new TransactionResultDTO(TransactionStatus.OTHER_PROBLEM.getCode(),
                    TransactionStatus.OTHER_PROBLEM.getDescription());
        }

        Account account = optionalAccount.get();

        BigDecimal amount = transaction.getTotalAmount();
        String merchant = transaction.getMerchant();
        String mccByMerchant = getMccByMerchant(merchant);
        String mcc = mccByMerchant != null ? mccByMerchant : transaction.getMcc();
        BigDecimal balance = getBalanceByMcc(account, mcc);

        if (balance.compareTo(amount) >= 0) {
            updateBalance(account, mcc, balance.subtract(amount));
            saveTransaction(transaction, account);
            logger.info("Transaction approved for account ID: {}", transaction.getAccount());
            return new TransactionResultDTO(TransactionStatus.APPROVED.getCode(),
                    TransactionStatus.APPROVED.getDescription());
        } else if (account.getCashBalance().compareTo(amount) >= 0) {
            account.setCashBalance(account.getCashBalance().subtract(amount));
            accountService.saveAccount(account);
            saveTransaction(transaction, account);
            logger.info("Transaction approved using CASH balance for account ID: {}", transaction.getAccount());
            return new TransactionResultDTO(TransactionStatus.APPROVED.getCode(),
                    TransactionStatus.APPROVED.getDescription());
        } else {
            logger.error("Insufficient funds for account ID: {}", transaction.getAccount());
            return new TransactionResultDTO(TransactionStatus.INSUFFICIENT_BALANCE.getCode(),
                    TransactionStatus.INSUFFICIENT_BALANCE.getDescription());
        }
    }

    private void saveTransaction(TransactionDTO transactionDTO, Account account) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(transactionDTO.getTotalAmount());
        transaction.setMerchant(transactionDTO.getMerchant());
        transaction.setMcc(transactionDTO.getMcc());
        transactionRepository.save(transaction);
    }

    private BigDecimal getBalanceByMcc(Account account, String mcc) {
        if (mcc == null) {
            return account.getCashBalance();
        }

        switch (mcc) {
            case "5411":
            case "5412":
                return account.getFoodBalance();
            case "5811":
            case "5812":
                return account.getMealBalance();
            default:
                return account.getCashBalance();
        }
    }

    private void updateBalance(Account account, String mcc, BigDecimal newBalance) {
        if (mcc == null) {
            account.setCashBalance(newBalance);
            accountService.saveAccount(account);
            return;
        }

        switch (mcc) {
            case "5411":
            case "5412":
                account.setFoodBalance(newBalance);
                break;
            case "5811":
            case "5812":
                account.setMealBalance(newBalance);
                break;
            default:
                account.setCashBalance(newBalance);
                break;
        }
        
        accountService.saveAccount(account);
    }

    private String getMccByMerchant(String merchant) {
        if (merchant != null
                && merchant.toLowerCase().matches("(?i).*\\b(mercado|supermarket|grocery|mercearia)\\b.*")) {
            return "5411";
        }

        if (merchant != null
                && merchant.toLowerCase().matches("(?i).*\\b(uber eats|restaurante|restaurant|meal)\\b.*")) {
            return "5812";
        }

        return null;
    }
}