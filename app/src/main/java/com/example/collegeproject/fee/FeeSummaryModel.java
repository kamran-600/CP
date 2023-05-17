package com.example.collegeproject.fee;

import com.google.firebase.firestore.Blob;

public class FeeSummaryModel {

    private final Blob imageBlob;
    private final String name;
    private final String rno;
    private final String total;
    private final String submit;



    public FeeSummaryModel(Blob imageBlob, String name, String rno, String total, String submit) {
        this.imageBlob = imageBlob;
        this.name = name;
        this.rno = rno;
        this.total = total;
        this.submit = submit;
    }

    public Blob getImageBlob() {
        return imageBlob;
    }

    public String getName() {
        return name;
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