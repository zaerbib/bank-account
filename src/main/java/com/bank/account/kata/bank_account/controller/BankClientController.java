package com.bank.account.kata.bank_account.controller;

import com.bank.account.kata.bank_account.dto.ClientOpDto;
import com.bank.account.kata.bank_account.model.Account;
import com.bank.account.kata.bank_account.model.BankClient;
import com.bank.account.kata.bank_account.model.Transaction;
import com.bank.account.kata.bank_account.service.BankClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankClientController {

    private final BankClientService clientService;

    public BankClientController(BankClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create/client")
    public ResponseEntity<BankClient> createClient(@RequestParam String clientId) {
        return ResponseEntity.ok(clientService.createClient(clientId));
    }

    @PostMapping("/create/account")
    public ResponseEntity<Account> createAccount(@RequestParam String clientId,
                                                 @RequestParam String accountId) {
        return ResponseEntity.ok(clientService.createAccount(clientId, accountId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody ClientOpDto op) {
        clientService.deposit(op.getClientId(), op.getAccountId(), op.getAmount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdrawal(@RequestBody ClientOpDto op) {
        clientService.withdraw(op.getClientId(), op.getAccountId(), op.getAmount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account")
    public ResponseEntity<Account> getAccount(@RequestParam String clientId,
                                              @RequestParam String accountId) {
        return ResponseEntity.ok(clientService.getAccount(clientId, accountId));
    }

    @GetMapping("/statement")
    public ResponseEntity<List<Transaction>> getStatement(@RequestParam String clientId,
                                                          @RequestParam String accountId) {
        return ResponseEntity.ok(clientService.getAccount(clientId, accountId).getStatement());
    }
}
