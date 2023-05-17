package com.example.collegeproject.feed.models;

import com.google.firebase.firestore.Blob;

public class TextImageFeedModel {
    private String  senderName,feedMsg, date, time, role, roll_number, email;
    private Blob feedImageByteBlob;

    public TextImageFeedModel() {
    }

    public TextImageFeedModel(String senderName, String feedMsg, String date, String time, String role, String roll_number, String email, Blob feedImageByteBlob) {
        this.senderName = senderName;
        this.feedMsg = feedMsg;
        this.date = date;
        this.time = time;
        this.role = role;
        this.roll_number = roll_number;
        this.email = email;
        this.feedImageByteBlob = feedImageByteBlob;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getFeedMsg() {
        return feedMsg;
    }

    public void setFeedMsg(String feedMsg) {
        this.feedMsg = feedMsg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public Blob getFeedImageByteBlob() {
        return feedImageByteBlob;
    }

    public void setFeedImageByteBlob(Blob feedImageByteBlob) {
        this.feedImageByteBlob = feedImageByteBlob;
    }
}