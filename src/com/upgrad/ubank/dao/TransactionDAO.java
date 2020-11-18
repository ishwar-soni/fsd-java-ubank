package com.upgrad.ubank.dao;

import com.upgrad.ubank.dtos.Transaction;

import java.sql.SQLException;

public interface TransactionDAO {
    public Transaction create (Transaction transaction) throws SQLException;
    public Transaction[] findByAccountNo (int accountNo) throws SQLException;
}
