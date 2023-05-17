package com.example.collegeproject.Assignment;


public class AssignmentModal {

    private String teacherName, id;
    private String className;
    private String desc;
    private String dueDate;
    private String date;
    private String time;
    private String assignmentUrl, email;

    public AssignmentModal() {
    }

    public AssignmentModal(String teacherName, String id, String className, String desc, String dueDate, String date, String time, String assignmentUrl, String email) {

        this.teacherName = teacherName;
        this.id = id;
        this.className = className;
        this.desc = desc;
        this.dueDate = dueDate;
        this.date = date;
        this.time = time;
        this.assignmentUrl = assignmentUrl;
        this.email = email;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
