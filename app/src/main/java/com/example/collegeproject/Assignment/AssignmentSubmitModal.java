package com.example.collegeproject.Assignment;

import com.google.firebase.firestore.Blob;

public class AssignmentSubmitModal {

    String studentName, submittedAssignmentUrl , date , time, roll_number;

    String checkedAssignmentUrl, checkedDate, checkedTime;
    /*// boolean expanded;

    public AssignmentSubmitModal(int image, String stuName, String submitDate, String submitTime) {
        this.image = image;
        this.stuName = stuName;
        this.submitDate = submitDate;
        this.submitTime = submitTime;
        // this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }*/

   public AssignmentSubmitModal() {
   }

    public AssignmentSubmitModal(String studentName, String submittedAssignmentUrl, String date, String time, String roll_number) {
        this.studentName = studentName;
        this.submittedAssignmentUrl = submittedAssignmentUrl;
        this.date = date;
        this.time = time;
        this.roll_number = roll_number;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubmittedAssignmentUrl() {
        return submittedAssignmentUrl;
    }

    public void setSubmittedAssignmentUrl(String submittedAssignmentUrl) {
        this.submittedAssignmentUrl = submittedAssignmentUrl;
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

    public String getCheckedAssignmentUrl() {
        return checkedAssignmentUrl;
    }

    public void setCheckedAssignmentUrl(String checkedAssignmentUrl) {
        this.checkedAssignmentUrl = checkedAssignmentUrl;
    }

    public String getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(String checkedDate) {
        this.checkedDate = checkedDate;
    }

    public String getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(String checkedTime) {
        this.checkedTime = checkedTime;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }
}
