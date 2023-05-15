package com.example.collegeproject.Contacts;

public class ContactModel {


    private int image;
    private String stuName;
    private String stuRNumber;
    private String stuCNumber;


    public ContactModel() {
    }

    public ContactModel(String stuRNumber) {
        this.stuRNumber = stuRNumber;
    }

    public ContactModel(int image, String stuName, String stuRNumber, String stuCNumber) {
        this.image = image;
        this.stuName = stuName;
        this.stuRNumber = stuRNumber;
        this.stuCNumber = stuCNumber;
    }

    public ContactModel(int image, String stuName, String stuCNumber) {
        this.image = image;
        this.stuName = stuName;
        this.stuCNumber = stuCNumber;
    }

    public ContactModel(String stuName, String stuCNumber) {
        this.stuName = stuName;
        this.stuCNumber = stuCNumber;
    }

    public ContactModel(String stuName, String stuRNumber, String stuCNumber) {
        this.stuName = stuName;
        this.stuRNumber = stuRNumber;
        this.stuCNumber = stuCNumber;
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

    public String getStuRNumber() {
        return stuRNumber;
    }

    public void setStuRNumber(String stuRNumber) {
        this.stuRNumber = stuRNumber;
    }

    public String getStuCNumber() {
        return stuCNumber;
    }

    public void setStuCNumber(String stuCNumber) {
        this.stuCNumber = stuCNumber;
    }
}
