package com.example.collegeproject.Chat;

public class ChatModel {

    private int image;
    private  String dName;
    private String aYear;
    private  String lastMsg;
    private  String time;

   public ChatModel(int image, String dName, String aYear, String lastMsg, String time){
        this.image = image;
        this.dName = dName;
        this.aYear = aYear;
        this.lastMsg = lastMsg;
        this.time = time;
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

    public String getLastMsg() {
        return lastMsg;
    }

    public String getTime() {
        return time;
    }
}
