package com.IntraPaymentTransfer.IntraPaymentTransfer.service;

import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.AccountRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.TransactionsRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.AccountNotFoundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.CreditAccountNotFoundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.DebitAccountNotFoundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.InsufficientFundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Transaction;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.TransactionPayload;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {
    @MockBean
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private TransactionsRepository transactionsRepository;

    private List<Account> accountList;
    private TransactionPayload testPayload;

    @Before
    public void init() {
        accountList = Arrays.asList(new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active"),
                new Account(222, "test2", new BigDecimal(200.00), "GBP", "Active"),
                new Account(333, "test3", new BigDecimal(300.00), "GBP", "Active"),
                new Account(444, "test4", new BigDecimal(400.00), "GBP", "Active"),
                new Account(555, "test5", new BigDecimal(500.00), "GBP", "Active")
        );
        accountList.forEach(account -> {
            accountRepository.save(account);
        });
        testPayload = new TransactionPayload();
        testPayload.setDebitAccount(111);
        testPayload.setCreditAccount(333);
        testPayload.setAmount(new BigDecimal(1.00));
        testPayload.setCurrency("GBP");
        testPayload.setPaymentNote("Intra transfer Payment");
        testPayload.setPaymentDate(new Date());
    }

    @Test(expected = DebitAccountNotFoundException.class)
    public void debitAccountNotFoundTest() {
        TransactionPayload testPayload = new TransactionPayload(1111, 333, new BigDecimal(1.00), "GBP", "Intra transfer Payment", new Date());
        transactionService.transferIntraPay(testPayload);
    }

    @Test
    public void getAllTransactionsTest() {
        List<Transaction> list = new ArrayList<>();
        Transaction debitTransaction = new Transaction(1, 111, new BigDecimal(100.00), "GBP", "DEBIT", "Intra transfer Payment", new Date(), LocalDateTime.now());
        list.add(debitTransaction);
        Transaction creditTransaction = new Transaction(2, 333, new BigDecimal(100.00), "GBP", "CREDIT", "Intra transfer Payment", new Date(), LocalDateTime.now());
        list.add(creditTransaction);
        Mockito.when(transactionsRepository.findAll()).thenReturn(list);
        Assert.assertEquals(2, transactionService.getAllTransactions().size());
        Assert.assertEquals(1, transactionService.getAllTransactions().get(0).getId());
        Assert.assertEquals(111, transactionService.getAllTransactions().get(0).getAccountId());
        Assert.assertEquals("GBP", transactionService.getAllTransactions().get(0).getCurrency());
        Assert.assertEquals("DEBIT", transactionService.getAllTransactions().get(0).getType());
        Assert.assertEquals("Intra transfer Payment", transactionService.getAllTransactions().get(0).getPaymentNote());
        Assert.assertEquals(new BigDecimal(100.00), transactionService.getAllTransactions().get(0).getAmount());
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Assert.assertEquals(parser.format(new Date()), parser.format(transactionService.getAllTransactions().get(0).getPaymentDate()));
    }

    @Test
    public void tranferIntraPayTest() {
        Account debitAccount = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(debitAccount));
        Account creditAccount = new Account(333, "test2", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(333)).thenReturn(java.util.Optional.of(creditAccount));
        transactionService.transferIntraPay(testPayload);
    }

    @Test(expected = CreditAccountNotFoundException.class)
    public void creditAccountNotFound() {
        Account debitAccount = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(debitAccount));
        transactionService.transferIntraPay(testPayload);
    }

    @Test(expected = RuntimeException.class)
    public void invalidAmountTest() {
        TransactionPayload testPayload = new TransactionPayload(111, 333, new BigDecimal(-1.00), "GBP", "Intra transfer Payment", new Date());
        Account debitAccount = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(debitAccount));
        Account creditAccount = new Account(333, "test2", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(333)).thenReturn(java.util.Optional.of(creditAccount));
        transactionService.transferIntraPay(testPayload);
    }

    @Test(expected = InsufficientFundException.class)
    public void insufficientFund() {
        Account debitAccount = new Account(111, "test1", new BigDecimal(0.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(debitAccount));
        Account creditAccount = new Account(333, "test2", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(333)).thenReturn(java.util.Optional.of(creditAccount));
        transactionService.transferIntraPay(testPayload);
    }

    @Test(expected = AccountNotFoundException.class)
    public void getMiniStatementTest1() {
        List<Transaction> list = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 111, new BigDecimal(100.00), "GBP", "DEBIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(transaction1);
        Transaction transaction2 = new Transaction(2, 333, new BigDecimal(100.00), "GBP", "CREDIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(transaction2);
        Mockito.when(transactionsRepository.findAll()).thenReturn(list);
        transactionService.getMiniStatement(222);
    }

    @Test
    public void getMiniStatementTest2() {
        Account debitAccount = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(debitAccount));
        List<Transaction> list = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 111, new BigDecimal(100.00), "GBP", "DEBIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(transaction1);
        Transaction transaction2 = new Transaction(2, 111, new BigDecimal(100.00), "GBP", "CREDIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(transaction2);
        Transaction transaction3 = new Transaction(3, 111, new BigDecimal(100.00), "GBP", "DEBIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(transaction3);
        Transaction transaction4 = new Transaction(4, 111, new BigDecimal(100.00), "GBP", "CREDIT", "Intra trasnfer Payment", new Date(), LocalDateTime.now());
        list.add(transaction4);
        Mockito.when(transactionsRepository.findTop20ByAccountIdOrderByTimestampDesc(111)).thenReturn(list);
        transactionService.getMiniStatement(111);
    }
}
