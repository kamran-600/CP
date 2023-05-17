package com.example.collegeproject.Contacts;

import com.google.firebase.firestore.Blob;

public class ContactModel {


    private Blob imageBlob;
    private String stuName;
    private String stuRNumber;
    private String stuCNumber;


    public ContactModel() {
    }

    public ContactModel(String stuRNumber) {
        this.stuRNumber = stuRNumber;
    }

    public ContactModel(Blob imageBlob, String stuName, String stuRNumber, String stuCNumber) {
        this.imageBlob = imageBlob;
        this.stuName = stuName;
        this.stuRNumber = stuRNumber;
        this.stuCNumber = stuCNumber;
    }

    public ContactModel(Blob imageBlob, String stuName, String stuCNumber) {
        this.imageBlob = imageBlob;
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


    public Blob getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(Blob imageBlob) {
        this.imageBlob = imageBlob;
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
