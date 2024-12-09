package com.example.biydaalt.model;

/**
 * Энэ нь лаборатори дахь шинжилгээний үр дүнгийн ангилал юм.
 * Шинжилгээний үр дүн нь ажил, дээж, эмчилгээний концентраци, шинжилгээний огноо гэсэн мэдээлэлтэй.
 */
public class TestResults {
    private String jobId;
    private String sampleId;
    private float medicalConcentration;
    private String testDate;

    public void reassignState() {
        // Шинжилгээний үр дүнгийн төлвийг дахин томилох функцийг хэрэгжүүлнэ.
    }

    public void exportCSV() {
        // Шинжилгээний үр дүнгийн мэдээллийг CSV файлаар экспортлох функцийг хэрэгжүүлнэ.
    }

    public boolean validateResults() {
        // Шинжилгээний үр дүнгийг баталгаажуулах функцийг хэрэгжүүлнэ.
        return true;
    }

    public void assignToJob(Job job) {
        // Шинжилгээний үр дүнг ажилд хавсаргах функцийг хэрэгжүүлнэ.
        this.jobId = job.getJobId();
    }

    public void assignToSample(Sample sample) {
        // Шинжилгээний үр дүнг дээжид хавсаргах функцийг хэрэгжүүлнэ.
        this.sampleId = sample.getSampleId();
    }

    // Бусад гэрч, тогтоогчид
}