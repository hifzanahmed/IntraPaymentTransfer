package com.IntraPaymentTransfer.IntraPaymentTransfer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Balance {
    private int accountId;
    private BigDecimal balance;
    private String currency;

}
