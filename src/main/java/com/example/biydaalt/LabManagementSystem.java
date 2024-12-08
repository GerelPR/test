package com.example.biydaalt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LabManagementSystem extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the root layout (BorderPane)
        BorderPane root = new BorderPane();

        // Create the sidebar menu
        VBox sidebarMenu = createSidebarMenu();
        root.setLeft(sidebarMenu);

        // Create the main content area
        StackPane mainContent = createMainContent();
        root.setCenter(mainContent);

        // Create the user profile section
        HBox userProfile = createUserProfile();
        root.setTop(userProfile);

        // Apply CSS styles
        root.getStylesheets().add(getClass().getResource("/com/example/style.css").toExternalForm());

        // Set the scene and show the stage
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab Management System");
        primaryStage.show();
    }

    private VBox createSidebarMenu() {
        // Implement the sidebar menu
        VBox sidebarMenu = new VBox();
        sidebarMenu.setPrefWidth(200); // Set a fixed width for the sidebar
        sidebarMenu.setStyle("-fx-background-color: #2c3e50;"); // Example styling
        return sidebarMenu;
    }

    private StackPane createMainContent() {
        // Implement the main content area
        StackPane mainContent = new StackPane();
        mainContent.setStyle("-fx-background-color: #ecf0f1;"); // Example styling
        return mainContent;
    }

    private HBox createUserProfile() {
        // Implement the user profile section
        HBox userProfile = new HBox();
        userProfile.setStyle("-fx-background-color: #34495e;"); // Example styling
        userProfile.setPrefHeight(50); // Example height
        return userProfile;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
