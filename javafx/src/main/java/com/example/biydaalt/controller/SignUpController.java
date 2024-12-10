package com.example.biydaalt.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.example.biydaalt.model.User;
import com.example.biydaalt.repository.DatabaseConnection;
import com.example.biydaalt.repository.UserRepository;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public SignUpController() {
        try {
            // Establish database connection
            Connection connection = DatabaseConnection.connect();
            if (connection != null && !connection.isClosed()) {
                this.userRepository = new UserRepository(connection);
            } else {
                System.err.println("Database connection is invalid.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unexpected error occurred while initializing the UserRepository.");
        }
    }

    // Validate email format
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // Validate password complexity
    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    // Handle user sign-up
    @FXML
    private void handleSignUp(ActionEvent event) throws IOException, SQLException {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Input validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }
        if (!isValidEmail(email)) {
            errorLabel.setText("Invalid email format.");
            return;
        }
        if (!isStrongPassword(password)) {
            errorLabel.setText("Password must be at least 8 characters long and contain uppercase, lowercase, number, and special character.");
            return;
        }

        try {
            // Generate a unique user ID
            String userId = UUID.randomUUID().toString();

            // Create a new user object with the five-argument constructor
            User newUser = new User(userId, name, email, password, "User");

            // Save the user in the database
            userRepository.createUser(newUser);

            // Success message
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText("Sign Up successful!");

            // Redirect to the login page
            redirectToLogin();
        } catch (IllegalArgumentException e) {
            // Handle validation error
            errorLabel.setText(e.getMessage());
        } catch (SQLException e) {
            // Handle database errors
            if (e.getMessage().contains("Duplicate entry")) {
                errorLabel.setText("Email or name already exists. Please use a different email or name.");
            } else {
                e.printStackTrace();
                errorLabel.setText("An error occurred while creating the user.");
            }
        }
    }

    // Redirect to the login page
    @FXML
    private void redirectToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginForm.fxml"));
        Parent loginRoot = loader.load();

        // Get the current stage and update its root
        Stage currentStage = (Stage) nameField.getScene().getWindow();
        currentStage.getScene().setRoot(loginRoot);
    }
}
