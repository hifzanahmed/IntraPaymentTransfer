package com.IntraPaymentTransfer.IntraPaymentTransfer.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @Column(unique=true)
    private int accountId;
    private String name;
    private BigDecimal balance;
    private String currency;
    private String status;

}
