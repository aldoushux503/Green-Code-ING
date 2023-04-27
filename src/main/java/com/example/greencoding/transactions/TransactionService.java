package com.example.greencoding.transactions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {

    private final Map<String, Account> accounts = new HashMap<>();

    public ResponseEntity<List<Account>> generateReport(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            String debitAccountNumber = transaction.getDebitAccount();
            String creditAccountNumber = transaction.getCreditAccount();
            BigDecimal amount = transaction.getAmount();

            Account debitAccount = getOrCreateAccount(debitAccountNumber);
            debitAccount.debit(amount);

            Account creditAccount = getOrCreateAccount(creditAccountNumber);
            creditAccount.credit(amount);
        }

        List<Account> sortedAccounts = new ArrayList<>(accounts.values());
        Collections.sort(sortedAccounts);
        return new ResponseEntity<>(sortedAccounts, HttpStatus.OK);
    }

    private Account getOrCreateAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            account = new Account(accountNumber);
            accounts.put(accountNumber, account);
        }
        return account;
    }
}
