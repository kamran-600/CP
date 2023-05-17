package com.example.collegeproject.attendance;

import com.google.firebase.firestore.Blob;

public class AttendanceModel {

    private Blob profileImageBlob;
    private String full_name;
    private String roll_number;

    public AttendanceModel() {
    }

    public AttendanceModel(String full_name, String roll_number) {
        this.full_name = full_name;
        this.roll_number = roll_number;
    }

    public AttendanceModel(Blob profileImageBlob, String full_name, String roll_number) {
        this.profileImageBlob = profileImageBlob;
        this.full_name = full_name;
        this.roll_number = roll_number;
    }

    public Blob getProfileImageBlob() {
        return profileImageBlob;
    }

    public void setProfileImageBlob(Blob profileImageBlob) {
        this.profileImageBlob = profileImageBlob;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }
}
