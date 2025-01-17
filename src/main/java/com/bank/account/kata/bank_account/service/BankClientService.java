package com.bank.account.kata.bank_account.service;

import com.bank.account.kata.bank_account.exception.AccountAlreadyExistException;
import com.bank.account.kata.bank_account.exception.AccountNotFoundException;
import com.bank.account.kata.bank_account.exception.ClientAlreadyExistException;
import com.bank.account.kata.bank_account.exception.ClientNotFoundException;
import com.bank.account.kata.bank_account.model.Account;
import com.bank.account.kata.bank_account.model.BankClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BankClientService {
    private final Map<String, BankClient> clients = new ConcurrentHashMap<>();

    public BankClient createClient(String clientId) {
        if(clients.get(clientId) != null) {
            throw new ClientAlreadyExistException("Client Already exist: "+clientId);
        }

        BankClient client = new BankClient(clientId);
        clients.put(clientId, client);
        return client;
    }

    public Account createAccount(String clientId, String accountId) {
        BankClient client = getClientOrThrow(clientId);
        long nbAccount = client.getAccounts()
                               .stream()
                               .filter(account -> account.getAccountId().equals(accountId))
                               .count();
        if(nbAccount > 0) {
            throw new AccountAlreadyExistException("Account already exist: "+accountId);
        }
        Account account = new Account(accountId);
        client.getAccounts().add(account);
        return account;
    }

    public void deposit(String clientId, String accountId, BigDecimal amount) {
        BankClient client = getClientOrThrow(clientId);
        Account account = getAccountOrThrow(clientId, accountId);
        account.deposit(amount);
    }

    public void withdraw(String clientId, String accountId, BigDecimal amount) {
        BankClient client = getClientOrThrow(clientId);
        Account account = getAccountOrThrow(clientId, accountId);
        account.withdraw(amount);
    }

    public Account getAccount(String clientId, String accountId) {
        return getAccountOrThrow(clientId, accountId);
    }

    private BankClient getClientOrThrow(String clientId) {
        return clients.computeIfAbsent(clientId, id -> {
            throw new ClientNotFoundException("Client not found: "+id);
        });
    }

    private Account getAccountOrThrow(String clientId, String accountId) {
        return clients.computeIfAbsent(clientId, id -> {
            throw new ClientNotFoundException("Client not found: "+id);
        }).getAccounts().stream().filter(account -> account.getAccountId().equals(accountId))
                .findFirst().orElseThrow(() -> new AccountNotFoundException("Account not found: "+accountId));
    }
}
