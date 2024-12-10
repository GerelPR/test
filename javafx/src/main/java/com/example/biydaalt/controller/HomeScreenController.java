package com.example.biydaalt.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

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
    public void initialize() {
        // Set Dashboard as default
        loadContent("Dashboard");
        setActiveButton(btnDashboard);
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
        // Handle logout logic
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
