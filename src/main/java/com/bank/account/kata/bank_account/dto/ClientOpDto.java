package com.bank.account.kata.bank_account.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientOpDto {
    private String clientId;
    private String accountId;
    private BigDecimal amount;
}
