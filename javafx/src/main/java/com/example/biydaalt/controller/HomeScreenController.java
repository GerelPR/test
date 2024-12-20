package com.example.biydaalt.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.biydaalt.model.Job;
import com.example.biydaalt.model.Sample;
import com.example.biydaalt.repository.DatabaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class HomeScreenController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnAddJob;
    @FXML
    private Button btnPrintBarcode;
    @FXML
    private Button btnInputResult;
    @FXML
    private Button btnSeeResult;
    @FXML
    private Button btnWeightJob;
    @FXML
    private Button btnImportJob;
    @FXML
    private Button btnSearchJob;
    @FXML
    private Button btnSettings;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    private TableView<Job> jobTableView;

    @FXML
    private TableColumn<Job, String> jobIdColumn;
    @FXML
    private TableColumn<Job, String> jobNameColumn;
    @FXML
    private TableColumn<Job, String> urgencyColumn;
    @FXML
    private TableColumn<Job, String> jobTypeColumn;
    @FXML
    private TableColumn<Job, String> createdByColumn;
    @FXML
    private TableColumn<Job, String> createdAtColumn;
    @FXML
    private TableColumn<Job, String> statusColumn;
    
    @FXML
    private FXMLLoader loader;

    private Connection connection;
    private String currentUsername;

    @FXML
    private void initialize() {
        this.connection = DatabaseConnection.connect();
        jobTableView = new TableView<Job>();
        showDashboard();
    }

    /**
     * Set the user data to display on the Home Screen.
     * 
     * @param username The username of the current user.
     * @param email The email of the current user.
     * @param role The role of the current user.
     */
    public void setUserData(String username, String email, String role) {
        this.currentUsername = username;
        usernameLabel.setText(username);
        userEmailLabel.setText(email);
        userRoleLabel.setText(role);
    }

    @FXML
    private void showDashboard() {
        loader = loadContent("Dashboard");
        setActiveButton(btnDashboard);
    }

    @FXML
    private void showAddJob() {
        FXMLLoader loader = loadContent("AddJob");
        if (loader != null) {
            AddJobController addJobController = loader.getController(); // Access the controller
            addJobController.setCurrentUser(currentUsername); // Pass the current username
            setActiveButton(btnAddJob); // Set the active button
        }
    }

    @FXML
    private void showPrintBarcode() {
        loadContent("PrintBarcode");
        setActiveButton(btnPrintBarcode);
    }

    @FXML
    private void showInputResult() {
        loadContent("InputResult");
        setActiveButton(btnInputResult);
    }

    @FXML
    private void showSeeResult() {
        loadContent("SeeResult");
        setActiveButton(btnSeeResult);
    }

    @FXML
    private void showWeightJob() {
        loadContent("WeightJob");
        setActiveButton(btnWeightJob);
    }

    @FXML
    private void showImportJob() {
        loadContent("ImportJob");
        setActiveButton(btnImportJob);
    }

    @FXML
    private void showSearchJob() {
        loadContent("SearchJob");
        setActiveButton(btnSearchJob);
    }

    @FXML
    private void showSettings() {
        loadContent("Settings");
        setActiveButton(btnSettings);
    }

    @FXML
    private void logoutUser() {
        try {
            // Load the login screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginForm.fxml"));
            Parent loginRoot = loader.load();
    
            // Get the current stage
            Stage currentStage = (Stage) contentArea.getScene().getWindow();
    
            // Check if the current stage is maximized
            boolean isMaximized = currentStage.isMaximized();
    
            // Get current stage dimensions
            double currentWidth = currentStage.getWidth();
            double currentHeight = currentStage.getHeight();
    
            // Set the login screen as the root of the current stage
            currentStage.getScene().setRoot(loginRoot);
    
            // Apply the current size to the new scene
            if (!isMaximized) {
                currentStage.setWidth(currentWidth);
                currentStage.setHeight(currentHeight);
            }
    
            // Keep the maximized state if applicable
            currentStage.setMaximized(isMaximized);
    
            // Optionally set the stage title back to "Login"
            currentStage.setTitle("Login");
    
            System.out.println("User logged out successfully!");
        } catch (IOException e) {
            System.err.println("Failed to load login screen.");
        }
    }

    private FXMLLoader loadContent(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlName + ".fxml"));
            Parent content = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(content);
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setActiveButton(Button activeButton) {
        // Reset styles for all buttons
        btnDashboard.setStyle(null);
        btnAddJob.setStyle(null);
        btnPrintBarcode.setStyle(null);
        btnInputResult.setStyle(null);
        btnSeeResult.setStyle(null);
        btnWeightJob.setStyle(null);
        btnImportJob.setStyle(null);
        btnSearchJob.setStyle(null);
        btnSettings.setStyle(null);

        // Highlight the active button
        activeButton.setStyle("-fx-background-color: #ffffff33; -fx-text-fill: white;");
    }
}
