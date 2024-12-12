package com.example.biydaalt.controller;

import java.sql.Connection;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {

    private Connection connection = DatabaseConnection.connect();

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
    private TableView<Sample> sampleTableView;

    @FXML
    private TableColumn<Sample, String> sampleIdColumn;
    @FXML
    private TableColumn<Sample, String> sampleNameColumn;
    @FXML
    private TableColumn<Sample, String> sampleDescriptionColumn;

    public void initialize() {
        // Bind table columns to Job properties
        jobIdColumn.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        jobNameColumn.setCellValueFactory(new PropertyValueFactory<>("jobName"));
        urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
        jobTypeColumn.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));   
        
        sampleTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); 
        sampleIdColumn.setCellValueFactory(new PropertyValueFactory<>("sampleId"));
        sampleNameColumn.setCellValueFactory(new PropertyValueFactory<>("sampleName"));
        sampleDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("sampleDescription"));

        loadJobs();
        jobTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadSamplesForJob(newValue.getJobId()); 
            } else {
                sampleTableView.getItems().clear(); // Clear samples when no job is selected
            }
        });
    }

    private void loadSamplesForJob(String jobId) {
        try {
            List<Sample> samples = fetchSamplesForJob(jobId);

            // Clear existing items and add new samples
            sampleTableView.getItems().clear();
            ObservableList<Sample> observableSamples = FXCollections.observableArrayList(samples);
            sampleTableView.setItems(observableSamples);
        } catch (SQLException e) {
            System.err.println("Error loading samples: " + e.getMessage());
        }
    }

    private List<Sample> fetchSamplesForJob(String jobId) throws SQLException {
        List<Sample> samples = new ArrayList<>();

        String sampleQuery = "SELECT * FROM samples WHERE job_id = '" + jobId + "'"; 
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sampleQuery)) {

            while (rs.next()) {
                String sampleId = rs.getString("sample_id");
                String weightString = rs.getString("weight");
                Double weight;
                if (weightString != null && !weightString.isEmpty()) { // Check for null AND empty string
                    try {
                        weight = Double.valueOf(weightString);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid weight format: " + weightString);
                        // Handle the error appropriately (e.g., set a default value, log, throw an exception)
                        weight = 0.0; // Example: set to 0 if parsing fails
                    }
                } else {
                    System.err.println("Weight is null or empty in the database.");
                    // Handle the null case (e.g., set a default value)
                    weight = 0.0; // Example: set to 0 if null
                }
                String result = rs.getString("result");

                Sample sample = new Sample(sampleId, weight, result); 
                samples.add(sample);
            }
        }

        return samples;
    }

    private void loadJobs() {
        try {
            List<Job> jobs = fetchJobs();

            // Clear existing items and add new jobs
            jobTableView.getItems().clear();
            ObservableList<Job> observableJobs = FXCollections.observableArrayList(jobs);
            jobTableView.setItems(observableJobs);
        } catch (SQLException e) {
            System.err.println("Error loading jobs: " + e.getMessage());
        }
    }

    private List<Job> fetchJobs() throws SQLException {
        List<Job> jobs = new ArrayList<>();

        String jobQuery = "SELECT * FROM jobs";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(jobQuery)) {

            while (rs.next()) {
                String jobId = rs.getString("id");
                String jobName = rs.getString("name");
                String urgency = rs.getString("urgency");
                String jobType = rs.getString("job_type");
                String createdBy = rs.getString("created_by");
                String createdAt = rs.getString("created_at");
                String status = rs.getString("status");

                Job job = new Job(jobId, jobName, urgency, jobType, createdBy, createdAt);
                job.setStatus(status);

                jobs.add(job);
            }
        }

        return jobs;
    }
}
