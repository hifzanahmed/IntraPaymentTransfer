package com.IntraPaymentTransfer.IntraPaymentTransfer.service;

import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.AccountRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.*;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Balance;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

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
        accountList.forEach(account -> {
            accountRepository.save(account);
        });
        accountService.addAccount(accountList);
    }

    @Test
    public void getBalanceTest() {
        Account account = new Account();
        account.setAccountId(111);
        account.setName("test1");
        account.setBalance(new BigDecimal(100.00));
        account.setCurrency("GBP");
        account.setStatus("Active");
        Balance balance = new Balance();
        balance.setAccountId(111);
        balance.setBalance(new BigDecimal(100.00));
        balance.setCurrency("GBP");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(account));
        assertThat(accountService.getBalance(111).getBalance()).isEqualTo(balance.getBalance());
        assertThat(accountService.getBalance(111).getCurrency()).isEqualTo(balance.getCurrency());
        assertThat(accountService.getBalance(111).getAccountId()).isEqualTo(balance.getAccountId());
    }

    @Test(expected = AccountNotFoundException.class)
    public void getBalanceAccountNotFoundTest() {
        Account account = new Account();
        account.setAccountId(111);
        account.setName("test1");
        account.setBalance(new BigDecimal(100.00));
        account.setCurrency("GBP");
        account.setStatus("Active");
        Balance balance = new Balance(111, new BigDecimal(100.00), "GBP");
        //Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(account));
        assertThat(accountService.getBalance(1111).getBalance()).isEqualTo(balance.getBalance());
    }

    @Test
    public void getAccountDetailTest() {
        Account account = new Account();
        account.setAccountId(111);
        account.setName("test1");
        account.setBalance(new BigDecimal(100.00));
        account.setCurrency("GBP");
        account.setStatus("Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(account));
        assertThat(accountService.getAccountDetail(111)).isEqualTo(account);
        assertThat(accountService.getAccountDetail(111).getStatus()).isEqualTo(account.getStatus());
        assertThat(accountService.getAccountDetail(111).getName()).isEqualTo(account.getName());
    }

    @Test(expected = AccountNotFoundException.class)
    public void getAccountDetailAccountNotFoundTest() {
        Account account = new Account();
        account.setAccountId(111);
        account.setName("test1");
        account.setBalance(new BigDecimal(100.00));
        account.setCurrency("GBP");
        account.setStatus("Active");
        Mockito.when(accountRepository.findById(111)).thenReturn(java.util.Optional.of(account));
        assertThat(accountService.getAccountDetail(112)).isEqualTo(account);
    }

    @Test
    public void getAllAccountsTest() {
        Mockito.when(accountRepository.findAll()).thenReturn(accountList);
        assertThat(accountService.getAllAccounts()).isEqualTo(accountList);
        Assert.assertEquals(accountService.getAllAccounts(), accountList);
    }

    @Test(expected = NoAccountPresentException.class)
    public void getAllAccountsNoAccountPresentTest() {
        assertThat(accountService.getAllAccounts()).isEqualTo(accountList);
    }

}
