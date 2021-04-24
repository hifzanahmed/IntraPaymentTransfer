package com.IntraPaymentTransfer.IntraPaymentTransfer.service;

import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    ResponseEntity<Object> addAccount(List<Account> accountList);

    ResponseEntity<Object> getBalance(Integer accountId);

    ResponseEntity<Object> getAccountDetail(Integer accountId);

    List<Account> getAccountDetail();
}
