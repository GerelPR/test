package com.example.biydaalt.model;

public class Sample {
    private String sampleId; // Unique sample ID
    private Double weight; // Weight of the sample (nullable, updated later)
    private String result; // Result of the sample analysis (nullable, updated later)

    public Sample(String sampleId) {
        if (sampleId == null || sampleId.trim().isEmpty()) {
            throw new IllegalArgumentException("Sample ID cannot be empty.");
        }
        this.sampleId = sampleId;
        this.weight = null; // Weight not set initially
        this.result = null; // Result not set initially
    }
    

    // Getters and Setters
    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        if (sampleId == null || sampleId.trim().isEmpty()) {
            throw new IllegalArgumentException("Sample ID cannot be empty.");
        }
        this.sampleId = sampleId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        if (weight != null && weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }
        this.weight = weight;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "sampleId='" + sampleId + '\'' +
                ", weight=" + (weight != null ? weight : "Not Set") +
                ", result='" + (result != null ? result : "Not Set") + '\'' +
                '}';
    }
}
