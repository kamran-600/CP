package com.example.collegeproject.attendance;

public class AttendanceModel {

    private int image;
    private String stuName;
    private String roll;

    public AttendanceModel(int image, String stuName, String roll) {
        this.image = image;
        this.stuName = stuName;
        this.roll = roll;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
