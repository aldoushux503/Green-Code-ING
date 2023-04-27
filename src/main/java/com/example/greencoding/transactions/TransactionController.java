package com.example.greencoding.transactions;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    private final Map<String, Account> accounts = new TreeMap<>();

    @PostMapping("/transactions/report")
    public List<Account> generateReport(@RequestBody List<Transaction> transactions) {
        transactions.forEach(transaction -> {
            String debitAccountNumber = transaction.getDebitAccount();
            String creditAccountNumber = transaction.getCreditAccount();
            BigDecimal amount = transaction.getAmount();

            Account debitAccount = getOrCreateAccount(debitAccountNumber);
            debitAccount.debit(amount);

            Account creditAccount = getOrCreateAccount(creditAccountNumber);
            creditAccount.credit(amount);
        });

        List<Account> sortedAccounts = new LinkedList<>(accounts.values());
        sortedAccounts.sort(Comparator.comparing(Account::getBalance).reversed());
        return sortedAccounts;
    }

    private Account getOrCreateAccount(String accountNumber) {
        return accounts.computeIfAbsent(accountNumber, Account::new);
    }
}
