package com.example.greencoding.transactions;

import java.math.BigDecimal;

public class Account implements Comparable<Account> {
    private final String number;

    private int debitCount = 0;
    private int creditCount = 0;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(String number) {
        this.number = number;
    }

    public void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
        debitCount++;
    }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
        creditCount++;
    }

    public String getNumber() {
        return number;
    }

    public int getDebitCount() {
        return debitCount;
    }

    public int getCreditCount() {
        return creditCount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public int compareTo(Account other) {
        return number.compareTo(other.number);
    }
}
