package com.example.collegeproject.Assignment;

public class AssignmentSubmitModal {

    int image;
    String stuName, submitDate, submitTime;
    // boolean expanded;

    public AssignmentSubmitModal(int image, String stuName, String submitDate, String submitTime) {
        this.image = image;
        this.stuName = stuName;
        this.submitDate = submitDate;
        this.submitTime = submitTime;
        // this.expanded = false;
    }

   /* public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    */

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
}
