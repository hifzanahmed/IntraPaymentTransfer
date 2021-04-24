package com.IntraPaymentTransfer.IntraPaymentTransfer.service.Impl;

import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.AccountRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.AccountNotFoundException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.exception.NoAccountPresentException;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Balance;
import com.IntraPaymentTransfer.IntraPaymentTransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void addAccount(List<Account> accountList) {
        accountList.forEach(account -> {
            accountRepository.save(account);
        });
    }

    @Override
    public Balance getBalance(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new AccountNotFoundException("Invalid account number");
        }
        Balance result = new Balance();
        result.setAccountId(account.getAccountId());
        result.setBalance(account.getBalance());
        result.setCurrency(account.getCurrency());
        return result;
    }

    @Override
    public Account getAccountDetail(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new AccountNotFoundException("Invalid account number");
        }
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        if (accountList.size() == 0) {
            throw new NoAccountPresentException("No Account present");
        }
        return accountList;
    }
}
