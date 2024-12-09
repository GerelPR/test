package com.example.biydaalt.model;

/**
 * Энэ нь лаборатори дахь шинжилгээний дээжний ангилал юм.
 * Дээж нь дугаар, жин, төлөв, багц дугаар, дээж бүртгэх, төлөвийг шинэчлэх, мэдээллийг авах гэсэн шинж чанаруудтай.
 */
public class Sample {
    private String sampleId;
    private float weight;
    private String status;
    private String batchCode;
    private boolean registerSample;

    public void updateStatus() {
        // Дээжний төлвийг шинэчлэх функцийг хэрэгжүүлнэ.
    }

    public void getSampleInfo() {
        // Дээжний мэдээллийг авах функцийг хэрэгжүүлнэ.
    }

    public void registerSample() {
        // Дээжийг бүртгэх функцийг хэрэгжүүлнэ.
    }
    public String getSampleId() {
        return this.sampleId;
    }

    // Бусад гэрч, тогтоогчид
}
