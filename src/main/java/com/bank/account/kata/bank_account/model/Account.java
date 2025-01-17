package com.bank.account.kata.bank_account.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private final String accountId;
    private BigDecimal balance;
    private final List<Transaction> transactions;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;

    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
        this.createdDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    public String getAccountId() {
        return this.accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getStatement() {
        return Collections.unmodifiableList(transactions);
    }

    public LocalDateTime getCreatedData() {
        return createdDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /***
     * deposit amoun, update balance, add new transaction
     * and update the lastUpdate value
     * @param amount
     */
    public void deposit(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive for deposit operation");
        }

        this.balance = this.balance.add(amount);
        this.transactions.add(
                new Transaction(
                        LocalDateTime.now(),
                        TransactionType.DEPOSIT,
                        amount,
                        this.balance));
        this.lastUpdateDate = this.transactions.get(this.transactions.size() - 1).getDate();
    }

    public void withdraw(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive for withdrawal operation");
        }
        if(amount.compareTo(this.balance) > 0) {
            throw new IllegalStateException("Inufficient funds");
        }

        this.balance = this.balance.subtract(amount);
        this.transactions.add(
                new Transaction(
                        LocalDateTime.now(),
                        TransactionType.WITHDRAWAL,
                        amount,
                        this.balance));
        this.lastUpdateDate = this.transactions.get(this.transactions.size() - 1).getDate();
    }
}
