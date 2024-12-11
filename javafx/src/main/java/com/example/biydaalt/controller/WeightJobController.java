package com.example.biydaalt.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.biydaalt.repository.DatabaseConnection;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WeightJobController {

    @FXML
    private TextField searchField;

    @FXML
    private TextField sampleIdField;

    @FXML
    private TextField weightField;

    @FXML
    private Button submitButton;

    private Connection connection;

    @FXML
    private void initialize() {
        // Initialize database connection
        connection = DatabaseConnection.connect();
    }

    @FXML
    private void handleSearchButton() {
        String jobId = searchField.getText();

        if (jobId.isEmpty()) {
            System.out.println("Search field is empty.");
            return;
        }

        searchSamplesByJobId(jobId);
    }

    private void searchSamplesByJobId(String jobId) {
        String query = "SELECT s.sample_id, s.weight FROM samples s WHERE s.job_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, jobId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Populate the fields with the retrieved sample data
                sampleIdField.setText(resultSet.getString("sample_id"));
                weightField.setText(String.valueOf(resultSet.getDouble("weight")));
            } else {
                System.out.println("No samples found for Job ID: " + jobId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmitButton() {
        String sampleId = sampleIdField.getText();
        String weight = weightField.getText();

        if (sampleId.isEmpty() || weight.isEmpty()) {
            System.out.println("Fields cannot be empty.");
            return;
        }

        try {
            double weightValue = Double.parseDouble(weight);
            processSampleData(sampleId, weightValue);

            // Clear fields after submission
            sampleIdField.clear();
            weightField.clear();
        } catch (NumberFormatException e) {
            System.out.println("Invalid weight input.");
        }
    }

    private void processSampleData(String sampleId, double weight) {
        System.out.println("Sample ID: " + sampleId);
        System.out.println("Weight: " + weight);
        // Implement logic to update or save sample data in the database
    }
}
