package com.example.biydaalt;

import java.sql.Connection;

import com.example.biydaalt.model.User;
import com.example.biydaalt.repository.DatabaseConnection;
import com.example.biydaalt.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        // Establish a connection to the database
        Connection connection = DatabaseConnection.connect();

        if (connection == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }

        // Create the UserRepository
        UserRepository userRepository = new UserRepository(connection);

        // Create a new user
        User newUser = new User("U123", "John Doe", "john.doe@example.com", "Admin");
        userRepository.createUser(newUser);

        // Retrieve the user from the database
        User retrievedUser = userRepository.getUserById("U123");
        if (retrievedUser != null) {
            System.out.println("Retrieved User: " + retrievedUser);
        }

        // Update the user's profile
        retrievedUser.setName("John Smith");
        retrievedUser.setEmail("john.smith@example.com");
        retrievedUser.setRole("Lab Manager");
        userRepository.updateUser(retrievedUser);

        // Retrieve the updated user
        User updatedUser = userRepository.getUserById("U123");
        if (updatedUser != null) {
            System.out.println("Updated User: " + updatedUser);
        }

        // Delete the user
        userRepository.deleteUser("U123");
    }
}
