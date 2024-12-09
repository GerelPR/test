package com.example.biydaalt.controller;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

import com.example.biydaalt.model.User;
import com.example.biydaalt.repository.DatabaseConnection;
import com.example.biydaalt.repository.UserRepository;

import javafx.application.Platform;
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
    private static final Logger LOGGER = Logger.getLogger(LoginFormController.class.getName());
    
    // Maximum login attempts before temporary lockout
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    // Lockout duration in minutes
    private static final int LOCKOUT_DURATION_MINUTES = 15;

    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label errorMessage;
    
    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    // Login attempt tracking
    private int failedAttempts = 0;
    private LocalDateTime lockoutEndTime = null;

    private UserRepository userRepository;

    public LoginFormController() {
        try {
            Connection connection = DatabaseConnection.connect();
            if (connection != null) {
                this.userRepository = new UserRepository(connection);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize UserRepository", e);
        }
    }

    @FXML
    private void initialize() {
        // Set up login button action
        loginButton.setOnAction(e -> handleLogin());
        signupButton.setOnAction(e -> redirectToSignup());
    }

    @FXML
    private void handleLogin() {
        // Reset error message
        errorMessage.setText("");
        errorMessage.setStyle("-fx-text-fill: red;");

        // Input validation
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Check for empty fields
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty");
            return;
        }

        // Validate input format
        if (!isValidInput(username)) {
            showError("Invalid username format");
            return;
        }

        // Check for active lockout
        if (isLockedOut()) {
            long minutesRemaining = java.time.Duration.between(
                LocalDateTime.now(), lockoutEndTime
            ).toMinutes();
            
            showError("Too many failed attempts. Please try again in " + 
                      minutesRemaining + " minutes.");
            return;
        }

        // Attempt login
        try {
            boolean loginSuccessful = authenticateUser(username, password);

            if (loginSuccessful) {
                // Reset failed attempts on successful login
                failedAttempts = 0;
                lockoutEndTime = null;
                
                // Log successful login
                LOGGER.info("Successful login for user: " + username);
                
                // Clear sensitive data
                passwordField.clear();
                
                // Transition to main screen
                Platform.runLater(this::goToMainScreen);
            } else {
                // Handle failed login attempt
                handleFailedLogin();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Login process error", e);
            showError("An unexpected error occurred");
        }
    }

    @FXML
    private void redirectToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            Parent root = loader.load();
    
            Stage stage = (Stage) usernameField.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load sign-up screen", e);
            errorMessage.setText("Failed to transition to the sign-up screen.");
        }
    }
    

    private boolean isValidInput(String input) {
        return input != null && 
               input.length() >= 3 && 
               input.length() <= 50 && 
               input.matches("^[a-zA-Z0-9_]+$");
    }

    // Modify the authenticateUser method to handle password verification
    private boolean authenticateUser(String username, String password) {
        try {
            User user = userRepository.getUserByName(username);
            
            if (user != null) {
                try {
                    // Explicitly catch the IllegalArgumentException
                    return BCrypt.checkpw(password, user.getPassword());
                } catch (IllegalArgumentException e) {
                    // Log the specific error
                    LOGGER.log(Level.SEVERE, "BCrypt verification error for user: " + username, e);
                    
                    // Optional: Add additional logging to help diagnose the issue
                    System.err.println("Stored password hash: " + user.getPassword());
                    System.err.println("Hash length: " + user.getPassword().length());
                    
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Authentication error", e);
            return false;
        }
    }

    // Reset lockout method to allow login attempts after lockout period
    private boolean isLockedOut() {
        if (lockoutEndTime == null) return false;
        
        // Check if lockout period has passed
        if (LocalDateTime.now().isAfter(lockoutEndTime)) {
            // Reset lockout and failed attempts
            lockoutEndTime = null;
            failedAttempts = 0;
            return false;
        }
        
        return true;
    }

    private void handleFailedLogin() {
        failedAttempts++;
        
        if (failedAttempts >= MAX_LOGIN_ATTEMPTS) {
            // Set lockout time
            lockoutEndTime = LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES);
            
            // Log security event
            LOGGER.warning("Multiple failed login attempts. Account temporarily locked.");
        }
        
        showError("Invalid username or password");
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setStyle("-fx-text-fill: red;");
    }

    private void goToMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/labManagement.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) usernameField).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Lab Management");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load main screen", e);
            showError("Failed to load main screen");
        }
    }
}