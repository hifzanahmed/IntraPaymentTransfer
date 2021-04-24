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
        accountService.addAccount(accountList);
        return ResponseEntity.ok().body("Test account(s) added to H2");
    }

    @GetMapping("accounts/{accountId}/balance")
    public ResponseEntity<Object> getBalance(@PathVariable Integer accountId) {
        return ResponseEntity.ok().body(accountService.getBalance(accountId));
    }

    @GetMapping("accounts/{accountId}/accountDetail")
    public ResponseEntity<Object> getAccountDetail(@PathVariable Integer accountId) {
        return ResponseEntity.ok().body(accountService.getAccountDetail(accountId));
    }

    @GetMapping("accounts/getAllAccounts")
    public ResponseEntity<Object> getAllAccounts() {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }
}
