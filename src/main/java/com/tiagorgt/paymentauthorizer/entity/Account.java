package com.tiagorgt.paymentauthorizer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal foodBalance;
    private BigDecimal mealBalance;
    private BigDecimal cashBalance;
}