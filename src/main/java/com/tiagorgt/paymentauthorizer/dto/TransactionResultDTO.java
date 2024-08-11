package com.tiagorgt.paymentauthorizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResultDTO {
    String code;
    String message;
}