package com.example.collegeproject.feed.models;

public class ImageFeedModel {
    private String  userName;
    private int profileImage,userPostImage;

    public ImageFeedModel(String userName, int profileImage, int userPostImage) {
        this.userName = userName;
        this.profileImage = profileImage;
        this.userPostImage = userPostImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
