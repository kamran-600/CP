package com.example.collegeproject.Assignment;


public class AssignmentModal {

    private int image;
    private String tName, id;
    private String className;
    private String desc;
    private String dueDate;
    private String date;
    private String time;
    private String assignmentUrl;

    public AssignmentModal() {
    }

    public AssignmentModal(int image, String tName, String id, String className, String desc, String dueDate, String date, String time, String assignmentUrl) {
        this.image = image;
        this.tName = tName;
        this.id = id;
        this.className = className;
        this.desc = desc;
        this.dueDate = dueDate;
        this.date = date;
        this.time = time;
        this.assignmentUrl = assignmentUrl;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public String getAssignmentUrl() {
        return assignmentUrl;
    }

    public void setAssignmentUrl(String assignmentUrl) {
        this.assignmentUrl = assignmentUrl;
    }
}
