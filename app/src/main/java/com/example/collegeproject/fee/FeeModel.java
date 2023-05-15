package com.example.collegeproject.fee;

public class FeeModel {
    private int image;
    private String dName;
    private String aYear;

    public FeeModel() {
    }

    public FeeModel(String aYear) {
        this.aYear = aYear;
    }

    public FeeModel(String dName, String aYear) {
        this.dName = dName;
        this.aYear = aYear;
    }

    public FeeModel(int image) {
        this.image = image;
    }

    public FeeModel(int image, String dName, String aYear) {
        this.image = image;
        this.dName = dName;
        this.aYear = aYear;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getaYear() {
        return aYear;
    }

    public void setaYear(String aYear) {
        this.aYear = aYear;
    }
}


