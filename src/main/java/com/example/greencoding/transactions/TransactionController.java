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

    private final Map<String, Account> accounts = new HashMap<>();

    @PostMapping("/transactions/report")
    public ResponseEntity<List<Account>> generateReport(@RequestBody List<Transaction> transactions) {
        return new ResponseEntity<>(null);
    }

}
