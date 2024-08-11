package com.tiagorgt.paymentauthorizer.dto;

import com.tiagorgt.paymentauthorizer.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO {
    private ErrorCode errorCode;
    private String message;
}