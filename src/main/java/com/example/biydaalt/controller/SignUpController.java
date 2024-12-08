package com.example.biydaalt.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

    public SignUpController(){
        try {
            // Initialize the UserRepository with a database connection
            Connection connection = DatabaseConnection.connect();
            if (connection != null) {
                this.userRepository = new UserRepository(connection);
            } else {
                System.err.println("Database connection returned null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unexpected error occurred while initializing the UserRepository.");
        }
    }

    @FXML
    private void handleSignUp(ActionEvent event) throws IOException, SQLException {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
    
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }
    
        try {
            // Create a new user
            User newUser = new User(name, email, password, "User");
    
            // Use the userRepository to save the user
            userRepository.createUser(newUser);
            errorLabel.setText("Sign Up successful!");
    
            // Redirect to the login page
            redirectToLogin();
        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred while creating the user.");
        }
    }
    
    @FXML
    private void redirectToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginForm.fxml"));
        Pane loginPane = loader.load();
        Stage currentStage = (Stage) nameField.getScene().getWindow();
        currentStage.setScene(new Scene(loginPane));
        currentStage.show();
    }
}
