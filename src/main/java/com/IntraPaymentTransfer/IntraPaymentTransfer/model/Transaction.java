package com.IntraPaymentTransfer.IntraPaymentTransfer.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @Column(unique=true)
    @GeneratedValue
    private int id;
    private int accountId;
    private BigDecimal amount;
    private String currency;
    private String type;
    private String paymentNote;
    private Date paymentDate;
    private LocalDateTime timestamp;

}
