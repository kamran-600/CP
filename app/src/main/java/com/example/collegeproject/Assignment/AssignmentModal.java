package com.example.collegeproject.Assignment;


public class AssignmentModal {

    private int image;
    private String tName;
    private String className;
    private String desc;
    private String dueDate;
    private String date;
    private String time;
    private String assignmentUrl;

    public AssignmentModal() {
    }

    public AssignmentModal(int image, String tName, String className, String desc, String dueDate, String date, String time, String assignmentUrl) {
        this.image = image;
        this.tName = tName;
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

    public String gettName() {
        return tName;
    }

    public String getClassName() {
        return className;
    }

    public String getDesc() {
        return desc;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAssignmentUrl() {
        return assignmentUrl;
    }
}
