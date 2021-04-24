package com.IntraPaymentTransfer.IntraPaymentTransfer.service.Impl;

import com.IntraPaymentTransfer.IntraPaymentTransfer.dao.AccountRepository;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Balance;
import com.IntraPaymentTransfer.IntraPaymentTransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> addAccount(List<Account> accountList) {
        accountList.forEach(account -> {
            accountRepository.save(account);
        });
        return ResponseEntity.ok().body("Test account(s) added to H2");
    }

    @Override
    public ResponseEntity<Object> getBalance(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return ResponseEntity.ok().body("Invalid account number");
        }
        Balance result = new Balance();
        result.setAccountId(account.getAccountId());
        result.setBalance(account.getBalance());
        result.setCurrency(account.getCurrency());
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Object> getAccountDetail(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return ResponseEntity.ok().body("Invalid account number");
        }
        return ResponseEntity.ok().body(account);
    }

    @Override
    public List<Account> getAccountDetail() {
        List<Account> accountList = accountRepository.findAll();
        return accountList;
    }
}
