package com.IntraPaymentTransfer.IntraPaymentTransfer.service;

import com.IntraPaymentTransfer.IntraPaymentTransfer.model.TransactionPayload;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    ResponseEntity<Object> transferIntraPay(TransactionPayload payload);

    List<Transaction> getAllTransactions();

    ResponseEntity<Object> getMiniStatement(Integer accountId);
}
