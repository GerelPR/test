package com.example.biydaalt.controller;

import com.example.biydaalt.model.Job;
import com.example.biydaalt.model.Sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;

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

    private ObservableList<String> parsedSampleIds = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Populate urgency combo box with items
        urgencyComboBox.setItems(FXCollections.observableArrayList("яаралтай", "энгийн"));
        urgencyComboBox.getSelectionModel().selectFirst(); // Set default value
    }

    @FXML
    private void previewSamples() {
        // Parse sample IDs from the text area
        String[] sampleIds = sampleTextArea.getText().split("\\r?\\n");
        parsedSampleIds.clear();
        for (String sampleId : sampleIds) {
            if (!sampleId.trim().isEmpty()) {
                parsedSampleIds.add(sampleId.trim());
            }
        }

        // Show parsed sample IDs in the list view
        sampleListView.setItems(parsedSampleIds);
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

            if (parsedSampleIds.isEmpty()) {
                showError("You must add at least one sample.");
                return;
            }

            // Create Job object
            Job job = new Job(jobId, jobName, urgency, jobType, "currentUser", "2024-12-10"); // Replace currentUser and date dynamically

            // Add samples to the job
            List<Sample> samples = new ArrayList<>();
            for (String sampleId : parsedSampleIds) {
                samples.add(new Sample(sampleId));
            }
            samples.forEach(job::addSample);

            // Simulate saving job and samples (replace with actual database call)
            saveJob(job);

            // Show success message
            showSuccess("Job and samples saved successfully!");

            // Clear form
            clearForm();
        } catch (Exception e) {
            showError("An error occurred while submitting the job.");
            e.printStackTrace();
        }
    }

    private void saveJob(Job job) {
        // Simulate saving to the database
        System.out.println("Job saved: " + job);
        for (Sample sample : job.getSamples()) {
            System.out.println("Sample saved: " + sample);
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
        parsedSampleIds.clear();
        sampleListView.setItems(parsedSampleIds);
    }
}
