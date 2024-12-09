package com.example.biydaalt.model;

/**
 * Энэ нь лаборатори дахь ажил, даалгаварын ангилал юм.
 * Ажил нь төрөл, приоритет, үүсгэсэн огноо, төлөв, сүүлийн өөрчлөлтийн хугацаа гэсэн шинж чанаруудтай.
 */
public class Job {
    private String jobId;
    private String type;
    private int priority;
    private String creationDate;
    private String status;
    private int hoursSinceLastChange;

    public String getJobId() {
        return this.jobId;
    }

    public void searchJob() {
        // Ажлын хайлтын функцийг хэрэгжүүлнэ.
    }

    public void updateJob() {
        // Ажлыг шинэчлэх функцийг хэрэгжүүлнэ. 
    }

    public void getJobStatus() {
        // Ажлын төлвийг авах функцийг хэрэгжүүлнэ.
    }

    public void createSample() {
        // Ажлын хүрээнд дээж бүртгэх функцийг хэрэгжүүлнэ.
    }
    // Getters and setters
}