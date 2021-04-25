package com.IntraPaymentTransfer.IntraPaymentTransfer.controller;

import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.AccountRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.service.AccountService;
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
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {

    @Autowired
    AccountController accountController;

    @Autowired
    AccountService accountService;

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
    public void addAccountTest() {
        Account account1 = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.save(account1)).thenReturn(null);
        Account account2 = new Account(222, "test2", new BigDecimal(200.00), "GBP", "Active");
        Mockito.when(accountRepository.save(account2)).thenReturn(null);
        Account account3 = new Account(333, "test3", new BigDecimal(300.00), "GBP", "Active");
        Mockito.when(accountRepository.save(account3)).thenReturn(null);
        Account account4 = new Account(444, "test4", new BigDecimal(400.00), "GBP", "Active");
        Mockito.when(accountRepository.save(account4)).thenReturn(null);
        Account account5 = new Account(555, "test5", new BigDecimal(500.00), "GBP", "Active");
        Mockito.when(accountRepository.save(account5)).thenReturn(null);
        ResponseEntity<Object> responseEntity = accountController.addAccount(accountList);

        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }


    @Test
    public void getBalanceTest() {
        Account account = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(account));
        ResponseEntity<Object> responseEntity = accountController.getBalance(111);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getAllAccountsTest() {
        Mockito.when(accountRepository.findAll()).thenReturn(accountList);
        ResponseEntity<Object> responseEntity = accountController.getAllAccounts();
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getAccountDetailTest() {
        Account account = new Account(111, "test1", new BigDecimal(100.00), "GBP", "Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(account));
        ResponseEntity<Object> responseEntity = accountController.getAccountDetail(111);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
