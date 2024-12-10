package com.example.biydaalt.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;

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
    private void initialize() {
        // Set default content (Dashboard) on load
        loadContent("Dashboard");
        setActiveButton(btnDashboard);
    }

    /**
     * Set the user data to display on the Home Screen.
     * 
     * @param username The username of the current user.
     * @param email The email of the current user.
     * @param role The role of the current user.
     */
    public void setUserData(String username, String email, String role) {
        usernameLabel.setText(username);
        userEmailLabel.setText(email);
        userRoleLabel.setText(role);
    }

    @FXML
    private void showDashboard() {
        loadContent("Dashboard");
        setActiveButton(btnDashboard);
    }

    @FXML
    private void showAddJob() {
        loadContent("AddJob");
        setActiveButton(btnAddJob);
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
    private void logout() {
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
            e.printStackTrace();
            System.err.println("Failed to load login screen.");
        }
    }
       

    private void loadContent(String fxmlName) {
        try {
            Parent content = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName + ".fxml"));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(content);
        } catch (IOException e) {
            e.printStackTrace();
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
