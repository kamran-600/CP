package com.example.collegeproject.Assignment;

public class AssignmentSubmitModal {

    int profileImage;
    String studentName, submittedAssignmentUrl , date , time;

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

    public AssignmentSubmitModal(int profileImage, String studentName, String submittedAssignmentUrl, String date, String time) {
        this.profileImage = profileImage;
        this.studentName = studentName;
        this.submittedAssignmentUrl = submittedAssignmentUrl;
        this.date = date;
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

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
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
}
