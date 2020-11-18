package com.upgrad.ubank.dao;

import com.upgrad.ubank.dtos.Account;

public interface AccountDAO {
    public Account findByAccountNo (int accountNo);
    public Account create (Account account);
    public Account updateBalance (Account account);
}
