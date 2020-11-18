package com.upgrad.ubank.services;

import com.upgrad.ubank.dtos.Transaction;

public interface TransactionService {
    Transaction createTransaction (Transaction transaction) throws Exception;
    Transaction[] getTransactions (int accountNo) throws Exception;
}
