package com.upgrad.ubank.services;

import com.upgrad.ubank.Transaction;

public class TransactionServiceImpl implements TransactionService{

    private String loggedInSystem;

    public String getLoggedInSystem() {
        return loggedInSystem;
    }

    public void setLoggedInSystem(String loggedInSystem) {
        this.loggedInSystem = loggedInSystem;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction[] getTransactions(int accountNo) {
        if (loggedInSystem.equals("MOBILE")) {
            return null;
        } else {
            return new Transaction[0];
        }
    }
}
