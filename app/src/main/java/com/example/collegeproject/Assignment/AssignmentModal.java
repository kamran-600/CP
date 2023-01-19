package com.example.collegeproject.Assignment;



public class AssignmentModal {

    private final int image;
    private final String tName;
    private final String className;
    private final String desc;
    private final String dueDate;
    private final String time;

    public AssignmentModal(int image, String tName, String className, String desc, String dueDate, String time) {
        this.image = image;
        this.tName = tName;
        this.className = className;
        this.desc = desc;
        this.dueDate = dueDate;
        this.time = time;
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

    public String getTime() {
        return time;
    }
}
