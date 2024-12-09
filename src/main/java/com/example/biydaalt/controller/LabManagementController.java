package com.example.biydaalt.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LabManagementController {

    @FXML
    private BorderPane root;

    @FXML
    private VBox sidebarMenu;

    @FXML
    private StackPane mainContent;

    @FXML
    private HBox userProfile;

    public void initialize() {
        // Dynamically set size properties
        root.setPrefSize(1200, 600);
        sidebarMenu.setPrefWidth(300);
        mainContent.setPrefHeight(500);
    }

    public static void start(String[] args) {
        try {
            FXMLLoader loader = new FXMLLoader(LabManagementController.class.getResource("/fxml/lab-management.fxml"));
            BorderPane root = loader.load();
            Scene scene = new Scene(root, 1200, 800);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Lab Management System");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start(args);
    }
}