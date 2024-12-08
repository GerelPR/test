package com.example.biydaalt.controller;

import java.io.IOException;
import java.sql.Connection;

import com.example.biydaalt.model.User;
import com.example.biydaalt.repository.DatabaseConnection;
import com.example.biydaalt.repository.UserRepository;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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

    // This method is called when the Sign Up button is pressed
    @FXML
    private void handleSignUp(ActionEvent event) throws IOException {
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

        // After successful sign up, redirect to the Login screen
        redirectToLogin();
    }
    
    // Method to load the login screen
    @FXML
    private void redirectToLogin() throws IOException {
        // Load the Login FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginForm.fxml"));
        Pane loginPane = loader.load();

        // Get the current stage (window)
        Stage currentStage = (Stage) nameField.getScene().getWindow();

        // Create a new scene with the Login screen
        Scene loginScene = new Scene(loginPane);

        // Set the new scene to the current stage
        currentStage.setScene(loginScene);
        currentStage.show();
    }
}
