package com.example.biydaalt.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.biydaalt.model.Job;
import com.example.biydaalt.repository.DatabaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

    public void initialize() {
        // Bind table columns to Job properties
        jobIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        jobNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
        jobTypeColumn.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load jobs into the TableView
        loadJobs();
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
