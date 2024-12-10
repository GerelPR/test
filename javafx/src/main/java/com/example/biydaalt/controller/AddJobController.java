package com.example.biydaalt.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.biydaalt.model.Job;
import com.example.biydaalt.model.Sample;
import com.example.biydaalt.repository.DatabaseConnection;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AddJobController {

    @FXML
    private TextField jobIdField;

    @FXML
    private TextField jobNameField;

    @FXML
    private ComboBox<String> urgencyComboBox;

    @FXML
    private TextField jobTypeField;

    @FXML
    private TextArea sampleTextArea;

    @FXML
    private ListView<String> sampleListView;

    private Connection connection;

    private List<Sample> samples = new ArrayList<>();

    private String currentUser;

    @FXML
    private void initialize() {
        // Populate urgency combo box
        urgencyComboBox.setItems(FXCollections.observableArrayList("urgent", "normal"));
        urgencyComboBox.getSelectionModel().selectFirst();

        // Initialize database connection
        connection = DatabaseConnection.connect();

        // Handle null connection explicitly
        if (connection == null) {
            showError("Failed to connect to the database. Please check your connection settings.");
        }
    }

    @FXML
    private void previewSamples() {
        // Parse sample IDs from the text area
        String[] sampleIds = sampleTextArea.getText().split("\\r?\\n");
        samples.clear();
        for (String sampleId : sampleIds) {
            if (!sampleId.trim().isEmpty()) {
                samples.add(new Sample(sampleId.trim()));
            }
        }

        // Update the ListView with parsed sample IDs
        sampleListView.setItems(FXCollections.observableArrayList(
                samples.stream().map(Sample::getSampleId).toList()
        ));
    }

    private boolean isJobIdUnique(String jobId) throws SQLException {
        String query = "SELECT COUNT(*) FROM jobs WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, jobId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        }
        return false;
    }

    @FXML
    private void submitJob() {
        try {
            // Validate inputs
            String jobId = jobIdField.getText().trim();
            String jobName = jobNameField.getText().trim();
            String urgency = urgencyComboBox.getValue();
            String jobType = jobTypeField.getText().trim();

            if (jobId.isEmpty() || jobName.isEmpty() || urgency == null || jobType.isEmpty()) {
                showError("All fields must be filled out.");
                return;
            }

            if (samples.isEmpty()) {
                showError("You must add at least one sample.");
                return;
            }

            if (!isJobIdUnique(jobId)) {
                showError("Job ID already exists. Please use a unique Job");
                return;
            }

            String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Create a Job object
            Job job = new Job(jobId, jobName, urgency, jobType, this.currentUser, currentDateTime);

            // Save job and samples to the database
            saveJobToDatabase(job);
            saveSamplesToDatabase(job.getJobId(), samples);

            // Show success message
            showSuccess("Job and samples saved successfully!");

            // Clear form
            clearForm();
        } catch (SQLException e) {
            // Check specific error message for better insight
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Duplicate entry")) {
                showError("Job ID already exists. Please use a unique Job ID.");
            } else {
                // Log the full stack trace for more details
                System.err.println("SQLException occurred: " + errorMessage);
                e.printStackTrace();
                showError("An error occurred while submitting the job. Please check the console for details.");
            }
        }
    }

    private void saveJobToDatabase(Job job) throws SQLException {
        String sql = "INSERT INTO jobs (id, name, urgency, job_type, created_by, created_at, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, job.getJobId());
            stmt.setString(2, job.getJobName());
            stmt.setString(3, job.getUrgency());
            stmt.setString(4, job.getJobType());
            stmt.setString(5, job.getCreatedBy());
            stmt.setString(6, job.getCreatedAt());
            stmt.setString(7, job.getStatus());
            stmt.executeUpdate();
        }
    }

    private void saveSamplesToDatabase(String jobId, List<Sample> samples) throws SQLException {
        String sql = "INSERT INTO samples (sample_id, job_id, weight, result) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Sample sample : samples) {
                stmt.setString(1, sample.getSampleId());
                stmt.setString(2, jobId);
                stmt.setNull(3, java.sql.Types.DOUBLE); // Weight is not set initially
                stmt.setNull(4, java.sql.Types.VARCHAR); // Result is not set initially
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void clearForm() {
        jobIdField.clear();
        jobNameField.clear();
        jobTypeField.clear();
        sampleTextArea.clear();
        sampleListView.getItems().clear();
        samples.clear();
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }
}
