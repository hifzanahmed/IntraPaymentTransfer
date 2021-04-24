package com.IntraPaymentTransfer.IntraPaymentTransfer.dao;

import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

    //@Query(nativeQuery = true, value = "select TOP 2 t from Transactions t where t.accountId = ?1 order by t.timestamp desc ")
    List<Transaction> findTop20ByAccountIdOrderByTimestampDesc(Integer accountId);
}

