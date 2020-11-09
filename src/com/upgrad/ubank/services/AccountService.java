package com.upgrad.ubank.services;

public interface AccountService {
    boolean login (int accountNo, String password);
    boolean register (int accountNo, String password);
}
