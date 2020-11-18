package com.upgrad.ubank.dao;

import com.upgrad.ubank.db.Database;
import com.upgrad.ubank.dtos.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDAOImpl implements TransactionDAO {

    private static TransactionDAOImpl instance;

    private TransactionDAOImpl () {}

    public static TransactionDAOImpl getInstance() {
        if (instance == null) {
            instance = new TransactionDAOImpl();
        }
        return instance;
    }

    @Override
    public Transaction create(Transaction transaction) throws SQLException {
        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO transaction (accountNo, date_, action, amount) VALUES (" +
                transaction.getAccountNo()+ ", '" +
                transaction.getDate()+ "', '" +
                transaction.getAction()+ "', " +
                transaction.getAmount()+ ")";
        statement.executeUpdate(sql);
        return transaction;
    }

    @Override
    public Transaction[] findByAccountNo(int accountNo) throws SQLException {
        Transaction[] transactions = new Transaction[100];
        int counter = 0;

        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM transaction WHERE accountNo = " + accountNo;
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Transaction temp = new Transaction();
            temp.setAccountNo(resultSet.getInt("accountNo"));
            temp.setDate(resultSet.getString("date_"));
            temp.setAction(resultSet.getString("action"));
            temp.setAmount(resultSet.getInt("amount"));
            transactions[counter++] = temp;
        }

        return transactions;
    }

    public static void main(String[] args) throws SQLException {
        TransactionDAOImpl transactionDAO = TransactionDAOImpl.getInstance();
        Transaction transaction1 = new Transaction(400001, "2020/11/18", "DEPOSIT", 10000);
        Transaction transaction2 = new Transaction(400001, "2020/11/18", "WITHDRAW", 2000);
        transactionDAO.create(transaction1);
        transactionDAO.create(transaction2);

        Transaction[] transactions = transactionDAO.findByAccountNo(400001);
        for (Transaction transaction: transactions) {
            if (transaction == null) {
                break;
            }
            System.out.println(transaction);
        }
    }
}
