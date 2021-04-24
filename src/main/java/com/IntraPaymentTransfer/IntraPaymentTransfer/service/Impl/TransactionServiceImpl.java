package com.IntraPaymentTransfer.IntraPaymentTransfer.service.Impl;

import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.AccountRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.TransactionsRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.AccountNotFoundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.CreditAccountNotFoundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.DebitAccountNotFoundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.InsufficientFundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.TransactionPayload;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Transaction;
import com.IntraPaymentTransfer.IntraPaymentTransfer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void transferIntraPay(TransactionPayload payload) {
        Account debitAccount = accountRepository.findById(payload.getDebitAccount()).orElse(null);
        Account creditAccount = accountRepository.findById(payload.getCreditAccount()).orElse(null);
        if (debitAccount == null) {
            throw new DebitAccountNotFoundException("Debit account " + payload.getDebitAccount() + " not found");
        }
        if (creditAccount == null) {
            throw new CreditAccountNotFoundException("Credit account " + payload.getCreditAccount() + " not found");
        }
        if ((debitAccount.getBalance().compareTo(payload.getAmount())) == -1) {
            throw new InsufficientFundException("Insufficient fund available for the account " + payload.getDebitAccount());
        }
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
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = transactionsRepository.findAll();
        return transactionList;
    }

    @Override
    public List<Transaction> getMiniStatement(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if(account==null){
            throw new AccountNotFoundException("Invalid account number " + accountId);
        }
        List<Transaction> transactionList = transactionsRepository.findTop20ByAccountIdOrderByTimestampDesc(accountId);
        return transactionList;
    }
}
