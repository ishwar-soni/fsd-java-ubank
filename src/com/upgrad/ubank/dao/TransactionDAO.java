package com.upgrad.ubank.dao;

import com.upgrad.ubank.dtos.Transaction;

public interface TransactionDAO {
    public Transaction create (Transaction transaction);
    public Transaction[] findByAccountNo (int accountNo);
}
