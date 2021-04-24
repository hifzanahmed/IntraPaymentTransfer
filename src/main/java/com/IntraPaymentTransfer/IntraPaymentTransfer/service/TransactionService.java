package com.IntraPaymentTransfer.IntraPaymentTransfer.service;

import com.IntraPaymentTransfer.IntraPaymentTransfer.model.TransactionPayload;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Transaction;

import java.util.List;

public interface TransactionService {
    void transferIntraPay(TransactionPayload payload);

    List<Transaction> getAllTransactions();

    List<Transaction> getMiniStatement(Integer accountId);
}
