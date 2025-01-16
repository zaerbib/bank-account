package com.bank.account.kata.bank_account.service;

import com.bank.account.kata.bank_account.exception.AccountNotFoundException;
import com.bank.account.kata.bank_account.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountService {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public Account createAccount(String clientId) {
        Account account = new Account(clientId);
        accounts.put(clientId, account);
        return account;
    }

    public void deposit(String clientId, BigDecimal amount) {
        Account account = getAccountOrThrowAccountNotFoundException(clientId);
        account.deposit(amount);
    }

    public void withdraw(String clientId, BigDecimal amount) {
        Account account = getAccountOrThrowAccountNotFoundException(clientId);
        account.withdraw(amount);
    }

    public Account getAccount(String clientId) {
        return getAccountOrThrowAccountNotFoundException(clientId);
    }

    private Account getAccountOrThrowAccountNotFoundException(String clientId) {
        return accounts.computeIfAbsent(clientId,
                    id -> {
                        throw new AccountNotFoundException("Account not found: " + id);
                    }
                );
    }
}
