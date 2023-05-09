package com.example.collegeproject.assignmentData;

public class AssignmentData {

    String assignmentUrl, className, date, desc, teacherName, time, dueDate;

    public AssignmentData() {
    }

    public AssignmentData(String assignmentUrl, String className, String date, String desc, String teacherName, String time, String dueDate) {
        this.assignmentUrl = assignmentUrl;
        this.className = className;
        this.date = date;
        this.desc = desc;
        this.teacherName = teacherName;
        this.time = time;
        this.dueDate = dueDate;
    }

    public String getAssignmentUrl() {
        return assignmentUrl;
    }

    public void setAssignmentUrl(String assignmentUrl) {
        this.assignmentUrl = assignmentUrl;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
