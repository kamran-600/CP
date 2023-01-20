package com.example.collegeproject.fee;

public class FeeSummaryModel {

    private final int image;
    private final String Name;
    private final String rno;
    private final String total;
    private final String submit;

    public FeeSummaryModel(int image, String Name, String rno, String total, String submit) {
        this.image = image;
        this.Name = Name;
        this.rno = rno;
        this.total = total;
        this.submit = submit;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return Name;
    }

    public String getRno() {
        return rno;
    }

    public String getTotal() {
        return total;
    }

    public String getSubmit() {
        return submit;
    }
}
