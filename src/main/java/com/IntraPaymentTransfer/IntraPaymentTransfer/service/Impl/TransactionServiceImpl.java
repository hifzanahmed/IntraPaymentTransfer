package com.IntraPaymentTransfer.IntraPaymentTransfer.service.Impl;

import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.AccountRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.TransactionsRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.TransactionPayload;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Transaction;
import com.IntraPaymentTransfer.IntraPaymentTransfer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> transferIntraPay(TransactionPayload payload) {
        Account debitAccount = accountRepository.findById(payload.getDebitAccount()).orElse(null);
        Account creditAccount = accountRepository.findById(payload.getCreditAccount()).orElse(null);
        if (debitAccount == null || creditAccount == null) {
            return ResponseEntity.badRequest().body("Debit or Credit account not present or belong Org");
        }
        if ((debitAccount.getBalance().compareTo(payload.getAmount())) == -1) {
            return ResponseEntity.badRequest().body("Insufficient funds to transfer");
        }
        else
            {
            //Update the debit account details(available balance)
            BigDecimal debitAccountBalance = debitAccount.getBalance().subtract(payload.getAmount());
            debitAccount.setBalance(debitAccountBalance);
            accountRepository.save(debitAccount);
            //Update the credit account details(available balance)
            BigDecimal creditAccountBalance = creditAccount.getBalance().add(payload.getAmount());
            creditAccount.setBalance(creditAccountBalance);
            accountRepository.save(creditAccount);
            //save debit transaction
            Transaction debitTransaction = new Transaction();
            debitTransaction.setType("DEBIT");
            debitTransaction.setAccountId(payload.getDebitAccount());
            debitTransaction.setAmount(payload.getAmount());
            debitTransaction.setCurrency(payload.getCurrency());
            debitTransaction.setPaymentDate(payload.getPaymentDate());
            debitTransaction.setPaymentNote(payload.getPaymentNote());
            debitTransaction.setTimestamp(LocalDateTime.now());
            transactionsRepository.save(debitTransaction);
            //save credit transaction
            Transaction creditTransaction = new Transaction();
            creditTransaction.setType("CREDIT");
            creditTransaction.setAccountId(payload.getCreditAccount());
            creditTransaction.setAmount(payload.getAmount());
            creditTransaction.setCurrency(payload.getCurrency());
            creditTransaction.setPaymentDate(payload.getPaymentDate());
            creditTransaction.setPaymentNote(payload.getPaymentNote());
            creditTransaction.setTimestamp(LocalDateTime.now());
            transactionsRepository.save(creditTransaction);
            return ResponseEntity.ok().body("Payment transferred successfully");
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = transactionsRepository.findAll();
        return transactionList;
    }

    @Override
    public ResponseEntity<Object> getMiniStatement(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if(account==null){
            return ResponseEntity.ok().body("Invalid account number");
        }
        List<Transaction> transactionList = transactionsRepository.findTop20ByAccountIdOrderByTimestampDesc(accountId);
        return ResponseEntity.ok().body(transactionList);
    }
}
