package com.tiagorgt.paymentauthorizer.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    @NotNull
    @PositiveOrZero
    private BigDecimal cashBalance;

    @NotNull
    @PositiveOrZero
    private BigDecimal foodBalance;

    @NotNull
    @PositiveOrZero
    private BigDecimal mealBalance;
}