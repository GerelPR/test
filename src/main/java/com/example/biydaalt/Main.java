package com.example.biydaalt;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.connect();
        if (connection != null) {
            System.out.println("Connection successful.");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
