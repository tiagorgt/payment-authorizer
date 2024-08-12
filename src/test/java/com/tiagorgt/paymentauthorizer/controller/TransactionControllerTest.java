package com.tiagorgt.paymentauthorizer.controller;

import com.tiagorgt.paymentauthorizer.dto.TransactionDTO;
import com.tiagorgt.paymentauthorizer.dto.TransactionResultDTO;
import com.tiagorgt.paymentauthorizer.enums.TransactionStatus;
import com.tiagorgt.paymentauthorizer.service.TransactionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testAuthorizeTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount("123");
        transactionDTO.setMcc("5811");
        transactionDTO.setMerchant("PADARIA DO ZE               SAO PAULO BR");
        transactionDTO.setTotalAmount(new BigDecimal(10));

        TransactionResultDTO transactionResultDTO = new TransactionResultDTO(
                TransactionStatus.APPROVED.getCode(),
                TransactionStatus.APPROVED.getDescription());

        when(transactionService.authorizeTransaction(any(TransactionDTO.class))).thenReturn(transactionResultDTO);

        ResponseEntity<TransactionResultDTO> response = transactionController.authorizeTransaction(transactionDTO);

        assertEquals(ResponseEntity.ok().body(transactionResultDTO), response);
    }
}