package com.example.biydaalt.controller;

import java.io.IOException;
import java.sql.Connection;

import com.example.biydaalt.model.User;
import com.example.biydaalt.repository.DatabaseConnection;
import com.example.biydaalt.repository.UserRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginFormController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessage;
    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Username and password cannot be empty!");
            return;
        }

        // Connect to the database and verify login
        Connection connection = DatabaseConnection.connect();
        if (connection == null) {
            errorMessage.setText("Failed to connect to the database.");
            return;
        }

        UserRepository userRepository = new UserRepository(connection);
        User user = userRepository.getUserByName(username);

        if (user != null && password.equals("password")) { // Update to hashed password checking
            // Successful login
            errorMessage.setStyle("-fx-text-fill: green;");
            errorMessage.setText("Login Successful!");

            // Transition to the main screen
            goToMainScreen();
        } else {
            errorMessage.setText("Invalid username or password.");
        }
    }

    private void goToMainScreen() {
        try {
            // Load the LabManagement FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/labManagement.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) usernameField).getScene().getWindow();
            stage.setScene(new Scene(root));

            // Optionally, set the stage title (if needed)
            stage.setTitle("Lab Management");

            // Show the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage.setText("Failed to transition to the main screen.");
        }
    }
}
