package com.bank.account.kata.bank_account.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private final int status;
    private final String message;
}
