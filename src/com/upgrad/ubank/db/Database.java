package com.upgrad.ubank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

    public static Connection getConnection () {
        if (connection == null) {
            String url = "jdbc:mysql://localhost/ubank";
            String username = "ishwar";
            String password = "ishwar";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected");
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL Driver not found. Please download and add the driver.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private Database() {}

    public static void main(String[] args) {
        Database.getConnection();
    }
}
