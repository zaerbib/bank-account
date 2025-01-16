package com.bank.account.kata.bank_account.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private final LocalDateTime date;
    private final TransactionType type;
    private final BigDecimal ammount;
    private final BigDecimal balance;;
}
