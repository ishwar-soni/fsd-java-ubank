package com.upgrad.ubank.services;

import com.upgrad.ubank.Account;

public class AccountService {
    //Account array to store account objects for the application, later in the course
    //this array will be replaced with database
    private Account[] accounts;

    //counter is used to track how many accounts are present in the account array
    private int counter;

    public AccountService () {
        accounts = new Account[100];
        counter = 0;
    }

    public boolean login (int accountNo, String password) {
        for (int i=0; i<counter; i++) {
            if (accountNo == accounts[i].getAccountNo() && password.equals(accounts[i].getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean register (int accountNo, String password) {
        for (int i=0; i<counter; i++) {
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
}
