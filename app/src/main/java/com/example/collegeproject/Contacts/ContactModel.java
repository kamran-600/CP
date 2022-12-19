package com.example.collegeproject.Contacts;

public class ContactModel {


    private int image;
    private  String stuName;
    private String stuRNumber;
    private  String stuCNumber;

    public ContactModel(int image, String stuName, String stuRNumber, String stuCNumber) {
        this.image = image;
        this.stuName = stuName;
        this.stuRNumber = stuRNumber;
        this.stuCNumber = stuCNumber;
    }

    public int getImage() {
        return image;
    }

    public String getStuName() {
        return stuName;
    }

    public String getStuRNumber() {
        return stuRNumber;
    }

    public String getStuCNumber() {
        return stuCNumber;
    }
}
