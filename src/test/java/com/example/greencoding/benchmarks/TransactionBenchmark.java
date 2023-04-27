package com.example.greencoding.benchmarks;

import com.example.greencoding.onlinegame.GameService;
import com.example.greencoding.transactions.Transaction;
import com.example.greencoding.transactions.TransactionService;
import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@State(Scope.Benchmark)
public class TransactionBenchmark {
    private static final List<Transaction> TRANSACTIONS;
    TransactionService transactionService;

    static {
        final String account1 = "32309111922661937852684864";
        final String account2 = "06105023389842834748547303";
        final String account3 = "66105036543749403346524547";
        TRANSACTIONS = new ArrayList<>();

        for (int i = 0; i < 50_000; i++) {
            TRANSACTIONS.add(new Transaction(account1, account3, BigDecimal.valueOf(60.10)));
            TRANSACTIONS.add(new Transaction(account1, account2, BigDecimal.valueOf(21.00)));
            TRANSACTIONS.add(new Transaction(account1, account3, BigDecimal.valueOf(70.56)));
            TRANSACTIONS.add(new Transaction(account2, account3, BigDecimal.valueOf(22.22)));
        }
    }

    @Setup
    public void setup() {
        transactionService = new TransactionService();
    }
    @Benchmark
    public void TransactionServiceMethodBenchmark() {

        transactionService.generateReport(TRANSACTIONS);
    }
}
