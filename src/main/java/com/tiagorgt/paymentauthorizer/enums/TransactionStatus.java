package com.tiagorgt.paymentauthorizer.enums;

public enum TransactionStatus {
    APPROVED("00", "Transaction approved"),
    INSUFFICIENT_BALANCE("51", "Transaction rejected due to insufficient balance"),
    OTHER_PROBLEM("07", "Transaction rejected due to other problems");

    private final String code;
    private final String description;

    TransactionStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}