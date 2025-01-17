package com.bank.account.kata.bank_account.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BankClient {
    private final String clientId;
    private List<Account> accounts;

    public BankClient(String clientId) {
        this.clientId = clientId;
        this.accounts = new ArrayList<>();
    }
}
