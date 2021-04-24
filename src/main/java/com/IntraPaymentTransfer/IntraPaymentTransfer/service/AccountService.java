package com.IntraPaymentTransfer.IntraPaymentTransfer.service;

import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Account;
import com.IntraPaymentTransfer.IntraPaymentTransfer.model.Balance;

import java.util.List;

public interface AccountService {
    void addAccount(List<Account> accountList);

    Balance getBalance(Integer accountId);

    Account getAccountDetail(Integer accountId);

    List<Account> getAllAccounts();
}
