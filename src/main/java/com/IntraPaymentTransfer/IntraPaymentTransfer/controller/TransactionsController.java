package com.IntraPaymentTransfer.IntraPaymentTransfer.controller;

import com.IntraPaymentTransfer.IntraPaymentTransfer.model.*;

import com.IntraPaymentTransfer.IntraPaymentTransfer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TransactionsController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/accounts/intraTransfer")
    public ResponseEntity<Object> transferIntraPay(@RequestBody TransactionPayload payload) {
        return transactionService.transferIntraPay(payload);
    }

    @GetMapping("accounts/getAllTransactions")
    public ResponseEntity<Object> getAllTransactions() {
        List<Transaction> transactionList = transactionService.getAllTransactions();
        if (transactionList.size() == 0 ) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(transactionList);
    }

    @GetMapping("accounts/{accountId}/getMiniStatement")
    public ResponseEntity<Object> getMiniStatement(@PathVariable Integer accountId) {
        return transactionService.getMiniStatement(accountId);
    }
}
