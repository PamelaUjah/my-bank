package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;


    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, TransactionType.DEPOSIT));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, TransactionType.WITHDRAWAL));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS -> {
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return (amount - 1000) * 0.002;
            }
            case MAXI_SAVINGS -> {
                for (Transaction transaction : transactions) {
                    if (transaction.getTransactionDate().isAfter(DateProvider.getInstance().tenDays())
                            && transaction.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
                        return amount * 0.001;
                    }
                }
                return amount * 0.05;
            }
            default -> {
                return amount * 0.001;
            }
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
