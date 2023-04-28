package com.example.greencoding.tests;

import com.example.greencoding.transactions.Account;
import com.example.greencoding.transactions.Transaction;
import com.example.greencoding.transactions.TransactionService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {
    private final TransactionService transactionService = new TransactionService();

    @ParameterizedTest
    @MethodSource("transactionTestData")
    public void generateReport_ReturnsListOfAccounts(List<Transaction> transactions, List<Account> expectedAccounts) {
        ResponseEntity<List<Account>> responseEntity = transactionService.generateReport(transactions);
        List<Account> accounts = responseEntity.getBody();
        assertNotNull(accounts);
        assertEquals(expectedAccounts.size(), accounts.size());
        for (int i = 0; i < expectedAccounts.size(); i++) {
            Account expectedAcc = expectedAccounts.get(i);
            Account actualAcc = accounts.get(i);

            assertEquals(expectedAcc.getNumber(), actualAcc.getNumber());
            assertEquals(expectedAcc.getDebitCount(), actualAcc.getDebitCount());
            assertEquals(expectedAcc.getCreditCount(), actualAcc.getCreditCount());
            assertEquals(expectedAcc.getBalance(), actualAcc.getBalance());
        }
    }

    private static Stream<Arguments> transactionTestData() {
        // Example test
        List<Transaction> transactions1 = Arrays.asList(
                new Transaction("32309111922661937852684864", "06105023389842834748547303", new BigDecimal("10.90")),
                new Transaction("31074318698137062235845814", "66105036543749403346524547", new BigDecimal("200.90")),
                new Transaction("66105036543749403346524547", "32309111922661937852684864", new BigDecimal("50.10"))
        );
        List<Account> expectedAccounts1 = Arrays.asList(
                new Account("06105023389842834748547303", 0, 1, new BigDecimal("10.90")),
                new Account("31074318698137062235845814", 1, 0, new BigDecimal("-200.90")),
                new Account("32309111922661937852684864", 1, 1, new BigDecimal("39.20")),
                new Account("66105036543749403346524547", 1, 1, new BigDecimal("150.80"))
        );

        List<Transaction> transactions2 = Arrays.asList(
                new Transaction("11111111111111111111111111", "22222222222222222222222222", new BigDecimal("100.00")),
                new Transaction("22222222222222222222222222", "33333333333333333333333333", new BigDecimal("50.00")),
                new Transaction("33333333333333333333333333", "44444444444444444444444444", new BigDecimal("25.00")),
                new Transaction("44444444444444444444444444", "11111111111111111111111111", new BigDecimal("10.00"))
        );
        List<Account> expectedAccounts2 = Arrays.asList(
                new Account("11111111111111111111111111", 1, 1, new BigDecimal("-90.00")),
                new Account("22222222222222222222222222", 1, 1, new BigDecimal("50.00")),
                new Account("33333333333333333333333333", 1, 1, new BigDecimal("25.00")),
                new Account("44444444444444444444444444", 1, 1, new BigDecimal("15.00"))
        );

        return Stream.of(
                Arguments.of(transactions1, expectedAccounts1),
                Arguments.of(transactions2, expectedAccounts2)
        );
    }
}
