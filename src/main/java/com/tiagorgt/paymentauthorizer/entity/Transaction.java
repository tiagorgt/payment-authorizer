package com.tiagorgt.paymentauthorizer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    private BigDecimal amount;
    private String merchant;
    private String mcc;
}