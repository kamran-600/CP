package com.example.collegeproject.feed.models;

public class TextImageFeedModel {
    private String  userName,userText;
    private int profileImage,userPostImage;

    public TextImageFeedModel(String userName, String userText, int profileImage, int userPostImage) {
        this.userName = userName;
        this.userText = userText;
        this.profileImage = profileImage;
        this.userPostImage = userPostImage;
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

    public int getUserPostImage() {
        return userPostImage;
    }

    public void setUserPostImage(int userPostImage) {
        this.userPostImage = userPostImage;
    }
}