package com.example.biydaalt.model;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private String jobId; // Unique job ID
    private String jobName; // Unique job name
    private String urgency; // Urgency level
    private String jobType; // Type of job
    private String createdBy; // User who created the job
    private String createdAt; // Timestamp when the job was created
    private String status; // Current status of the job
    private List<Sample> samples; // List of samples associated with the job

    // Constructor
    public Job(String jobId, String jobName, String urgency, String jobType, String createdBy, String createdAt) {
        if (jobId == null || jobId.trim().isEmpty()) {
            throw new IllegalArgumentException("Job ID cannot be empty.");
        }
        if (jobName == null || jobName.trim().isEmpty()) {
            throw new IllegalArgumentException("Job name cannot be empty.");
        }
        if (!urgency.equals("urgent") && !urgency.equals("normal")) {
            throw new IllegalArgumentException("Urgency must be 'urgent' or 'normal'.");
        }
        if (jobType == null || jobType.trim().isEmpty()) {
            throw new IllegalArgumentException("Job type cannot be empty.");
        }

        this.jobId = jobId;
        this.jobName = jobName;
        this.urgency = urgency;
        this.jobType = jobType;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.status = "Weighting"; // Default status
        this.samples = new ArrayList<>();
    }

    // Getters and Setters
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty.");
        }
        this.status = status;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void addSample(Sample sample) {
        if (sample == null) {
            throw new IllegalArgumentException("Sample cannot be null.");
        }
        this.samples.add(sample);
    }

    public void updateSample(String sampleId, Double weight, String result) {
        for (Sample sample : samples) {
            if (sample.getSampleId().equals(sampleId)) {
                if (weight != null) {
                    sample.setWeight(weight);
                }
                if (result != null) {
                    sample.setResult(result);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Sample with ID " + sampleId + " not found.");
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", urgency='" + urgency + '\'' +
                ", jobType='" + jobType + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", status='" + status + '\'' +
                ", samples=" + samples +
                '}';
    }
}
