package com.bank.account.kata.bank_account.service;

import com.bank.account.kata.bank_account.exception.ClientNotFoundException;
import com.bank.account.kata.bank_account.model.Account;
import com.bank.account.kata.bank_account.model.BankClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BankClientServiceTest {
    private BankClientService clientService;
    private static final String CLIENT_ID = "test-client";
    private static final String ACCOUNT_ID = "test-account";

    @BeforeEach
    void setUp() {
        clientService = new BankClientService();
    }

    @Test
    @DisplayName("Should create new client")
    void should_create_new_client() {
        BankClient client = clientService.createClient(CLIENT_ID);
        assertNotNull(client);
        assertEquals(CLIENT_ID, client.getClientId());
        assertEquals(0, client.getAccounts().size());
    }

    @Test
    @DisplayName("Should create new account for client")
    void should_create_new_account_for_client() {
        BankClient client = clientService.createClient(CLIENT_ID);
        clientService.createAccount(CLIENT_ID, ACCOUNT_ID);
        Account account = clientService.getAccount(CLIENT_ID, ACCOUNT_ID);
        assertNotNull(account);
        assertEquals(ACCOUNT_ID, account.getAccountId());
    }

    @Test
    @DisplayName("Should throw exception when client not found")
    void should_throw_exception_when_client_not_found() {
        assertThrows(ClientNotFoundException.class,
                () -> clientService.getClient("non-existent"));
    }

    @Test
    @DisplayName("Should successfully deposit to existing account")
    void should_deposit_to_existing_account() {
        clientService.createClient(CLIENT_ID);
        clientService.createAccount(CLIENT_ID, ACCOUNT_ID);
        BigDecimal amount = new BigDecimal("100.00");

        clientService.deposit(CLIENT_ID, ACCOUNT_ID, amount);
        Account account = clientService.getAccount(CLIENT_ID, ACCOUNT_ID);

        assertEquals(amount, account.getBalance());
    }

    @Test
    @DisplayName("Should successfully withdraw from existing account")
    void should_withdraw_from_existing_account() {
        clientService.createClient(CLIENT_ID);
        clientService.createAccount(CLIENT_ID, ACCOUNT_ID);
        BigDecimal depositAmount = new BigDecimal("100.00");
        BigDecimal withdrawAmount = new BigDecimal("60.00");

        clientService.deposit(CLIENT_ID, ACCOUNT_ID, depositAmount);
        clientService.withdraw(CLIENT_ID, ACCOUNT_ID, withdrawAmount);

        Account account = clientService.getAccount(CLIENT_ID, ACCOUNT_ID);
        assertEquals(new BigDecimal("40.00"), account.getBalance());
    }
}
