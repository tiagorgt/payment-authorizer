package com.tiagorgt.paymentauthorizer.controller;

import com.tiagorgt.paymentauthorizer.dto.TransactionDTO;
import com.tiagorgt.paymentauthorizer.dto.TransactionResultDTO;
import com.tiagorgt.paymentauthorizer.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResultDTO> authorizeTransaction(@Valid @RequestBody TransactionDTO transaction) {
        TransactionResultDTO transactionResultDTO = transactionService.authorizeTransaction(transaction);
        return ResponseEntity.ok().body(transactionResultDTO);
    }
}