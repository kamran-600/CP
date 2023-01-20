package com.example.collegeproject.fee;

public class FeeModel {
        private int image;
        private  String dName;
        private String aYear;


    public FeeModel(int image, String dName, String aYear) {
        this.image = image;
        this.dName = dName;
        this.aYear = aYear;

    }

    public int getImage() {
        return image;
    }

    public String getdName() {
        return dName;
    }

    public String getaYear() {
        return aYear;
    }



}


