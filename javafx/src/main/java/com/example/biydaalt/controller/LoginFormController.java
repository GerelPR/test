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

/**
 * @author Unuu
 * LoginFormController handles user login functionalities.
 * It validates user input, manages authentication, and navigates users to the main screen or sign-up page.
 * This controller interacts with UserRepository to retrieve and verify user credentials.
 * Logs are implemented for security and debugging purposes.
 */
public class LoginFormController {
    private static final Logger LOGGER = Logger.getLogger(LoginFormController.class.getName());
    
    // Maximum login attempts before temporary lockout
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    // Lockout duration in minutes
    private static final int LOCKOUT_DURATION_MINUTES = 15;

    @FXML
    private TextField usernameField; // Field for entering username
    
    @FXML
    private PasswordField passwordField; // Field for entering password
    
    @FXML
    private Label lblErrorMessage; // Label to display error messages
    
    @FXML
    private Button btnLogin; // Button to trigger login
    
    @FXML
    private Button btnSignup; // Button to navigate to the sign-up page

    // Tracks login attempts and lockout timing
    private int failedAttempts = 0;
    private LocalDateTime lockoutEndTime = null;

    private UserRepository userRepository;

    /**
     * Constructor initializes the controller and connects to the database.
     * It sets up the UserRepository for retrieving user data.
     */
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

    /**
     * Initializes the controller after the FXML file is loaded.
     * Sets up button actions for login and sign-up functionalities.
     */
    @FXML
    private void initialize() {
        btnLogin.setOnAction(e -> handleLogin());
        btnSignup.setOnAction(e -> redirectToSignup());
    }

    /**
     * Handles the login process by validating input, authenticating the user,
     * and navigating to the main screen upon success.
     */
    @FXML
    private void handleLogin() {
        resetErrorMessage(); // Resets the error message


        String username = usernameField.getText().trim(); // Get input username
        String password = passwordField.getText(); // Get input password

        if (!validateInput(username, password)) {
            return; // Validation failure stops execution
        }

        if (isLockedOut()) {
            long minutesRemaining = java.time.Duration.between(LocalDateTime.now(), lockoutEndTime).toMinutes();
            showError("Too many failed attempts. Please try again in " + minutesRemaining + " minutes.");
            return;
        }

        try {
            boolean loginSuccessful = authenticateUser(username, password);
            if (loginSuccessful) {
                User currentUser = userRepository.getUserByName(username);
                if (currentUser != null) {
                    goToMainScreen(username, currentUser.getEmail(), currentUser.getRole());
                }
            } else {
                handleFailedLogin();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Login process error", e);
            showError("An unexpected error occurred");
        }
    }

    private void resetErrorMessage() {
        lblErrorMessage.setText("");
        lblErrorMessage.setStyle("-fx-text-fill: red;");
    }
    
    private boolean validateInput(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty");
            return false;
        }
    
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            showError("Invalid username format");
            return false;
        }
    
        return true;
    }

    /**
     * Redirects the user to the sign-up screen.
     */
    @FXML
    private void redirectToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load sign-up screen", e);
            lblErrorMessage.setText("Failed to transition to the sign-up screen.");
        }
    }

    /**
     * Validates the format of user input to ensure security and consistency.
     * @param input The input string to validate.
     * @return true if the input is valid, false otherwise.
     */
    private boolean isValidInput(String input) {
        return input != null && input.matches("^[a-zA-Z0-9_]+$");
    }

    /**
     * Authenticates the user credentials by checking them against the database.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if authentication is successful; false otherwise.
     */
    private boolean authenticateUser(String username, String password) {
        try {
            User user = userRepository.getUserByName(username);
            return user != null && BCrypt.checkpw(password, user.getPassword());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Authentication error", e);
            return false;
        }
    }

    /**
     * Checks if the user is currently locked out due to multiple failed attempts.
     * @return true if the user is locked out; false otherwise.
     */
    private boolean isLockedOut() {
        if (lockoutEndTime == null) return false;
        if (LocalDateTime.now().isAfter(lockoutEndTime)) {
            lockoutEndTime = null;
            failedAttempts = 0;
            return false;
        }
        return true;
    }

    /**
     * Handles failed login attempts by incrementing the counter and applying lockout if necessary.
     */
    private void handleFailedLogin() {
        failedAttempts++;
        if (failedAttempts >= MAX_LOGIN_ATTEMPTS) {
            lockoutEndTime = LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES);
            LOGGER.warning("Multiple failed login attempts. Account temporarily locked.");
        }
        showError("Invalid username or password");
    }

    /**
     * Displays an error message to the user.
     * @param message The error message to display.
     */
    private void showError(String message) {
        lblErrorMessage.setText(message);
        lblErrorMessage.setStyle("-fx-text-fill: red;");
    }

    /**
     * Navigates the user to the main dashboard screen.
     * @param username The username of the user.
     * @param email The email of the user.
     * @param role The role of the user.
     */
    private void goToMainScreen(String username, String email, String role) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomeScreen.fxml"));
            Parent root = loader.load();
            HomeScreenController homeScreenController = loader.getController();
            homeScreenController.setUserData(username, email, role);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setMaximized(true);
            stage.setTitle("Lab Management");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load main screen", e);
            showError("Failed to load main screen");
        }
    }
}
