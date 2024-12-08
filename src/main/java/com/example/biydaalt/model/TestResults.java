package com.example.biydaalt.model;

public class TestResults {
    private String jobId;
    private String sampleId;
    private float medicalConcentration;
    private String testDate;

    public void reassignState() {
        // Implement test results reassignment
    }

    public void exportCSV() {
        // Implement test results export to CSV
    }

    public boolean validateResults() {
        // Implement test results validation
        return true;
    }

    public void assignToJob(Job job) {
        // Assign test results to a job
        this.jobId = job.getJobId();
    }

    public void assignToSample(Sample sample) {
        // Assign test results to a sample
        this.sampleId = sample.getSampleId();
    }

    // Getters and setters
}