package com.upgrad.ubank.services;

import com.upgrad.ubank.Account;
import com.upgrad.ubank.Transaction;

public class AccountServiceImpl implements AccountService {
    //Account array to store account objects for the application, later in the course
    //this array will be replaced with database
    private Account[] accounts;

    //counter is used to track how many accounts are present in the account array
    private int counter;

    public AccountServiceImpl() {
        accounts = new Account[100];
        counter = 0;
    }

    public boolean login(int accountNo, String password) {
        for (int i = 0; i < counter; i++) {
            if (accountNo == accounts[i].getAccountNo() && password.equals(accounts[i].getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean register(int accountNo, String password) {
        for (int i = 0; i < counter; i++) {
            if (accountNo == accounts[i].getAccountNo()) {
                return false;
            }
        }

        Account temp = new Account();
        temp.setAccountNo(accountNo);
        temp.setPassword(password);
        temp.setBalance(0);
        accounts[counter++] = temp;
        return true;
    }

    @Override
    public Account getAccount(int accountNo) {
        return null;
    }

    @Override
    public Account deposit(int accountNo, int amount) {
        return null;
    }

    @Override
    public Account withdraw(int accountNo, int amount) {
        return null;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction[] getTransactions(int accountNo) {
        return new Transaction[0];
    }
}
