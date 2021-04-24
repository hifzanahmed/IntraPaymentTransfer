package com.IntraPaymentTransfer.IntraPaymentTransfer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionPayload {
    private int debitAccount;
    private int creditAccount;
    private BigDecimal amount;
    private String currency;
    private String paymentNote;
    private Date paymentDate;
}
