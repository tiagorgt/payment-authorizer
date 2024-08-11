package com.tiagorgt.paymentauthorizer.repository;

import com.tiagorgt.paymentauthorizer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}