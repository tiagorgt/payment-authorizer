package com.tiagorgt.paymentauthorizer.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {    
    @NotNull
    private String account;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal totalAmount;
    
    @NotNull
    private String merchant;

    private String mcc;
}