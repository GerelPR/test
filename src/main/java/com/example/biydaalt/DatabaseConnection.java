package com.example.biydaalt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection connect() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Database URL and credentials
            String url = "jdbc:mysql://localhost:3306/lab_management_system"; // Replace with your DB details
            String user = "admin"; // Replace with your DB username
            String password = "password"; // Replace with your DB password

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            
            System.out.println("Database connected successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
