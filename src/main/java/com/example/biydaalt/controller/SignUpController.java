package com.example.biydaalt.controller;

import java.sql.Connection;

import com.example.biydaalt.model.User;
import com.example.biydaalt.repository.DatabaseConnection;
import com.example.biydaalt.repository.UserRepository;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private UserRepository userRepository;

    // This method is called when the Sign Up button is pressed
    @FXML
    private void handleSignUp(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }

        // Assuming you have a method to get a database connection
        Connection connection = DatabaseConnection.connect();

        if (connection == null) {
            errorLabel.setText("Database connection error.");
            return;
        }
    
        // Now you can create a new UserRepository and pass the connection
        UserRepository userRepository = new UserRepository(connection);
    
        // Create a new user and store it in the database
        User newUser = new User(name, email, password, "User");
        userRepository.createUser(newUser);
    
        errorLabel.setText("Sign Up successful!");
    }
}
