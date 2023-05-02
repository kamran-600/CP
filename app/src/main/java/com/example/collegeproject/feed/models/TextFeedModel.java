package com.example.collegeproject.feed.models;

public class TextFeedModel {
    private String userName,userText;
    private int profileImage;

    public TextFeedModel(String userName, String userText, int profileImage) {
        this.userName = userName;
        this.userText = userText;
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }
}
