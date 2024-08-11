
package com.tiagorgt.paymentauthorizer.repository;

import com.tiagorgt.paymentauthorizer.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}