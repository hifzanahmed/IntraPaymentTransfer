package com.IntraPaymentTransfer.IntraPaymentTransfer.dao;

import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
