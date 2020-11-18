package com.upgrad.ubank.services;

import com.upgrad.ubank.dao.AccountDAO;
import com.upgrad.ubank.dao.DAOFactory;
import com.upgrad.ubank.dtos.Account;
import com.upgrad.ubank.dtos.Transaction;
import com.upgrad.ubank.exceptions.AccountAlreadyRegisteredException;
import com.upgrad.ubank.exceptions.AccountNotFoundException;
import com.upgrad.ubank.exceptions.IncorrectPasswordException;
import com.upgrad.ubank.exceptions.InsufficientBalanceException;
import com.upgrad.ubank.interfaces.Observer;
import com.upgrad.ubank.interfaces.Subject;

import java.sql.SQLException;

public class AccountServiceImpl implements AccountService, Subject {

    private static AccountServiceImpl instance;

    private Observer[] observers;
    private int counterObservers;

    private DAOFactory daoFactory;
    private AccountDAO accountDAO;

    private AccountServiceImpl () {
        observers = new Observer[100];
        counterObservers = 0;

        daoFactory = new DAOFactory();
        accountDAO = daoFactory.getAccountDAO();
    }

    public static AccountServiceImpl getInstance() {
        if (instance == null) {
            ServiceFactory serviceFactory = new ServiceFactory();
            instance = new AccountServiceImpl();
        }
        return instance;
    }

    public boolean login (Account account) throws Exception {
        if (account == null) {
            throw new NullPointerException("Account object was null");
        }

        Account temp = null;
        try {
            temp = accountDAO.findByAccountNo(account.getAccountNo());
        } catch (SQLException e) {
            throw new Exception("Some unexpected exception occurred.");
        }

        if (temp == null) {
            throw new AccountNotFoundException("Account no doesn't exist.");
        } else if (!temp.getPassword().equals(account.getPassword())) {
            throw new IncorrectPasswordException("Password is not correct.");
        } else {
            return true;
        }
    }

    public boolean register (Account account) throws Exception {
        if (account == null) {
            throw new NullPointerException("Account object was null");
        }
        Account temp = null;
        try {
            temp = accountDAO.findByAccountNo(account.getAccountNo());
        } catch (SQLException e) {
            throw new Exception ("Some unexpected exception occurred");
        }

        if (temp != null) {
            throw new AccountAlreadyRegisteredException("Account no already registered.");
        } else {
            accountDAO.create(account);
            return true;
        }
    }

    @Override
    public Account getAccount(int accountNo) throws Exception {
        Account account = null;
        try {
            account = accountDAO.findByAccountNo(accountNo);
        } catch (SQLException e) {
            throw new Exception("Some unexpected exception occurred.");
        }
        if (account == null) {
            throw new AccountNotFoundException("Account " + accountNo + " doesn't exists.");
        } else {
            return account;
        }
    }

    @Override
    public Account deposit(int accountNo, int amount) throws Exception {
        Account account = getAccount(accountNo);

        account.setBalance(account.getBalance() + amount);
        accountDAO.updateBalance(account);

        Transaction transaction = new Transaction();
        transaction.setAccountNo(accountNo);
        transaction.setDate("DD/MM/YYYY");
        transaction.setAction("Deposit ");
        transaction.setAmount(amount);
        notifyObservers(transaction);

        return account;
    }

    /*
     * A account holder can overdraw up to 1000 rs. This decision was made on
     * 13 July 2020. Please refer the business documents for more information.
     */
    @Override
    public Account withdraw(int accountNo, int amount) throws Exception {
        Account account = getAccount(accountNo);
        if (account == null) {
            return null;
        }
        if ((account.getBalance() + 1000) < amount) {
            throw new InsufficientBalanceException("Can't withdraw " + amount + " when balance is " + account.getBalance());
        }
        account.setBalance(account.getBalance() - amount);
        accountDAO.updateBalance(account);

        Transaction transaction = new Transaction();
        transaction.setAccountNo(accountNo);
        transaction.setDate("DD/MM/YYYY");
        transaction.setAction("Withdraw");
        transaction.setAmount(amount);
        notifyObservers(transaction);

        return account;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers[counterObservers++] = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = 0;
        for (; i<counterObservers; i++) {
            if (observers[i].equals(observer)) {
                break;
            }
        }
        if (i < counterObservers) {
            for (; i<counterObservers; i++) {
                observers[i] = observers[i+1];
            }
            counterObservers--;
        }
    }

    @Override
    public void notifyObservers(Object data) {
        for (Observer observer: observers) {
            if (observer == null) {
                break;
            }
            observer.update(data);
        }
    }
}
