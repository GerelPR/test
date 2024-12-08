package com.example.biydaalt;

import java.sql.Connection;

import com.example.biydaalt.model.User;
import com.example.biydaalt.repository.DatabaseConnection;
import com.example.biydaalt.repository.UserRepository;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginForm extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create the layout
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Create the form components
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");

        // Add components to the layout
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(errorMessage, 1, 3);

        // Set up the login button action
        loginButton.setOnAction(e -> {
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
            User user = userRepository.getUserById(username);

            if (user != null && password.equals("password")) { // This should be changed to check a hashed password
                // Successful login
                errorMessage.setStyle("-fx-text-fill: green;");
                errorMessage.setText("Login Successful!");

                // You can now transition to another screen or perform further actions
                goToMainScreen(primaryStage);
            } else {
                // Invalid credentials
                errorMessage.setText("Invalid username or password.");
            }
        });

        // Create the scene
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to transition to the main screen after login
    private void goToMainScreen(Stage primaryStage) {
        // Create the main screen or transition to your main application window
        Label mainLabel = new Label("Welcome to the Lab Management System!");
        Scene mainScene = new Scene(mainLabel, 400, 300);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Main Screen");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
