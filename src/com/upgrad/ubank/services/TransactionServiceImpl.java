package com.upgrad.ubank.services;

import com.upgrad.ubank.dtos.Transaction;
import com.upgrad.ubank.interfaces.Observer;
import com.upgrad.ubank.interfaces.Subject;

public class TransactionServiceImpl implements TransactionService, Observer {

    private static TransactionServiceImpl instance = new TransactionServiceImpl();

    private Transaction[] transactions;
    private int counter;
    private Subject accountServiceSubject;
    private ServiceFactory serviceFactory;

    private TransactionServiceImpl () {
        transactions = new Transaction[100];
        counter = 0;

        serviceFactory = new ServiceFactory();
        accountServiceSubject = (Subject) serviceFactory.getAccountService();
        accountServiceSubject.registerObserver(this);
    }

    public static TransactionServiceImpl getInstance() {
        if (instance == null) {
            instance = new TransactionServiceImpl();
        }
        return instance;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transactions[counter++] = transaction;
        return transaction;
    }

    @Override
    public Transaction[] getTransactions(int accountNo) {
        Transaction[] temp = new Transaction[100];
        int counterTemp = 0;
        for (int i=0; i<counter; i++) {
            if (transactions[i].getAccountNo() == accountNo) {
                temp[counterTemp++] = transactions[i];
            }
        }
        return temp;
    }

    @Override
    public void update(Object data) {
        if (data instanceof Transaction) {
            Transaction temp = (Transaction) data;
            createTransaction(temp);
        }
    }
}
