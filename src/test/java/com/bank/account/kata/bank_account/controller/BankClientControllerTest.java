package com.bank.account.kata.bank_account.controller;

import com.bank.account.kata.bank_account.model.Account;
import com.bank.account.kata.bank_account.model.BankClient;
import com.bank.account.kata.bank_account.service.BankClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankClientControllerTest {

    @Mock
    private BankClientService clientService;

    private BankClientController clientController;
    private static final String CLIENT_ID = "client-id";
    private static final String ACCOUNT_ID = "account-id";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientController = new BankClientController(clientService);
    }

    @Test
    @DisplayName("Should create client")
    void should_create_client() {
        BankClient client = new BankClient(CLIENT_ID);
        when(clientService.createClient(CLIENT_ID)).thenReturn(client);

        ResponseEntity<BankClient> response = clientController.createClient(CLIENT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(CLIENT_ID, response.getBody().getClientId());
        verify(clientService).createClient(CLIENT_ID);
    }

    @Test
    @DisplayName("Should create account for client")
    void should_create_account_for_client() {
        Account account = new Account(ACCOUNT_ID);
        when(clientService.createAccount(CLIENT_ID, ACCOUNT_ID)).thenReturn(account);

        ResponseEntity<Account> response = clientController.createAccount(CLIENT_ID, ACCOUNT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ACCOUNT_ID, response.getBody().getAccountId());
        verify(clientService).createAccount(CLIENT_ID, ACCOUNT_ID);
    }
}
