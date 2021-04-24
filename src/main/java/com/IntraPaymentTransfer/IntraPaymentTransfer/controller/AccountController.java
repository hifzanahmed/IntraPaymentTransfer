package com.IntraPaymentTransfer.IntraPaymentTransfer.controller;

import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;

import com.IntraPaymentTransfer.IntraPaymentTransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/accounts/addAccount")
    public ResponseEntity<Object> addAccount(@RequestBody List<Account> accountList) {
        return accountService.addAccount(accountList);
    }

    @GetMapping("accounts/{accountId}/balance")
    public ResponseEntity<Object> getBalance(@PathVariable Integer accountId) {
        return accountService.getBalance(accountId);
    }

    @GetMapping("accounts/{accountId}/accountDetail")
    public ResponseEntity<Object> getAccountDetail(@PathVariable Integer accountId) {
        return accountService.getAccountDetail(accountId);
    }

    @GetMapping("accounts/getAllAccounts")
    public ResponseEntity<List<Account>> getAccountDetail() {
        List<Account> accountList = accountService.getAccountDetail();
        if (accountList.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(accountList);
    }
}
