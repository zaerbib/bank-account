package com.bank.account.kata.bank_account.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account;
    private static final String ACCOUNT_ID = "test-account";

    @BeforeEach
    public void setUp(){
        account = new Account(ACCOUNT_ID);
    }

    @Test
    @DisplayName("Should create account with zero initial balance")
    void should_create_account_with_zero_balance() {
       assertEquals(BigDecimal.ZERO, account.getBalance());
       assertEquals(ACCOUNT_ID, account.getAccountId());
       assertTrue(account.getStatement().isEmpty());
    }

    @Test
    @DisplayName("Should successfully deposit positive amount")
    void should_deposit_positive_amount() {
        BigDecimal amount = new BigDecimal("100.00");
        account.deposit(amount);

        assertEquals(amount, account.getBalance());
        List<Transaction> statement = account.getStatement();
        assertEquals(1, statement.size());

        Transaction transaction = statement.get(0);
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals(amount, transaction.getAmount());
        assertEquals(amount, transaction.getBalance());
    }

    @Test
    @DisplayName("Should throw exception when depositing negative amount")
    void should_throw_exception_on_negative_deposit() {
        BigDecimal negativeAmount = new BigDecimal("-100.00");
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(negativeAmount));
    }

    @Test
    @DisplayName("Should throw exception when depositing zero")
    void should_throw_exception_on_zero_deposit() {
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Should successfully withdraw valid amount")
    void should_withdraw_valid_amount() {
        BigDecimal depositAmount = new BigDecimal("100.00");
        BigDecimal withdrawAmount = new BigDecimal("60.00");
        account.deposit(depositAmount);
        account.withdraw(withdrawAmount);

        assertEquals(new BigDecimal("40.00"), account.getBalance());
        List<Transaction> statement = account.getStatement();
        assertEquals(2, statement.size());

        Transaction transaction = statement.get(1);
        assertEquals(TransactionType.WITHDRAWAL, transaction.getType());
        assertEquals(withdrawAmount, transaction.getAmount());
        assertEquals(new BigDecimal("40.00"), transaction.getBalance());
    }

    @Test
    @DisplayName("Should throw exception when withdrawing more than balance")
    void should_throw_exception_on_insufficient_funds() {
        account.deposit(new BigDecimal("50.00"));
        assertThrows(IllegalStateException.class,
                () -> account.withdraw(new BigDecimal("60.00")));
    }

    @Test
    @DisplayName("Should throw exception when withdrawing negative amount")
    void should_throw_exception_on_negative_withdrawal() {
        account.deposit(new BigDecimal("100.00"));
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(new BigDecimal("-50.00")));
    }
}
