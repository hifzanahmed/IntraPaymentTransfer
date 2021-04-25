package com.IntraPaymentTransfer.IntraPaymentTransfer.controller;

import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.AccountRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.TransactionsRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Transaction;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.TransactionPayload;
import com.IntraPaymentTransfer.IntraPaymentTransfer.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionControllerTest {
    @Autowired
    TransactionsController transactionsController;

    @Autowired
    TransactionService transactionService;

    @MockBean
    private TransactionsRepository transactionsRepository;

    @MockBean
    private AccountRepository accountRepository;

    private List<Account> accountList;

    @Before
    public void init() {
        accountList = Arrays.asList(new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active"),
                new Account(222, "test2", new BigDecimal(200.00), "GBP", "Active"),
                new Account(333, "test3", new BigDecimal(300.00), "GBP", "Active"),
                new Account(444, "test4", new BigDecimal(400.00), "GBP", "Active"),
                new Account(555, "test5", new BigDecimal(500.00), "GBP", "Active")
        );
    }

    @Test
    public void getAllTransactionsTest() {
        List<Transaction> list = new ArrayList<>();
        Transaction debitTransaction = new Transaction(1, 111, new BigDecimal(100.00), "GBP", "DEBIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(debitTransaction);
        Transaction creditTransaction = new Transaction(2, 333, new BigDecimal(100.00), "GBP", "CREDIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(creditTransaction);
        Mockito.when(transactionsRepository.findAll()).thenReturn(list);
        ResponseEntity<Object> responseEntity = transactionsController.getAllTransactions();
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getMiniStatementTest() {
        Account debitAccount = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(debitAccount));
        List<Transaction> list = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 111, new BigDecimal(100.00), "GBP", "DEBIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(transaction1);
        Transaction transaction2 = new Transaction(2, 333, new BigDecimal(100.00), "GBP", "CREDIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(transaction2);
        Mockito.when(transactionsRepository.findTop20ByAccountIdOrderByTimestampDesc(111)).thenReturn(list);
        ResponseEntity<Object> responseEntity = transactionsController.getMiniStatement(111);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getMiniStatementNoResultTest() {
        Account debitAccount = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(debitAccount));
        List<Transaction> list = new ArrayList<>();
        Mockito.when(transactionsRepository.findAll()).thenReturn(list);
        ResponseEntity<Object> responseEntity = transactionsController.getAllTransactions();
        Assert.assertEquals(204, responseEntity.getStatusCodeValue());
    }

    @Test
    public void tranferIntraPayTest() {
        Account debitAccount = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(debitAccount));
        Account creditAccount = new Account(333, "test2", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(333)).thenReturn(java.util.Optional.of(creditAccount));
        ResponseEntity<Object> responseEntity = transactionsController.transferIntraPay(
                new TransactionPayload(111, 333, new BigDecimal(1.00), "GBP", "Intra transfer Payment", new Date()));
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }
}

